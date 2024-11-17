package ro.bible.htmlparser.service;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.tuples.Tuple2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.bible.localfiles.util.BibleSourceDocuments;
import ro.bible.reporting.service.ConsoleReportWriterService;
import ro.bible.htmlparser.util.BibleStringUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChapterExtractorService {
    public static final String VERSE_NUMBER_AND_TEXT_STRUCTURE_PATTERN = "(\\d{1,3})\\s*\\.?\\s*(.+\\w{3,}.+)";
    private final String bookName;
    private final String chapterName;
    private final String chapterKeyName;

    public ChapterExtractorService(String bookName, String chapterName, String chapterKeyName) {
        this.bookName = bookName;
        this.chapterName = chapterName;
        this.chapterKeyName = chapterKeyName;
    }

    public static List<String> inlineLostTexts(List<String> strings) {
        List<String> result = new ArrayList<>();
        StringBuilder currentVerse = new StringBuilder();

        for (String line : strings) {
            // Check if the line starts with a number followed by a period (like "24." or "25.")
            if (line.matches("\\d+\\.*.*")) {
                // If currentVerse is not empty, add it to the result list
                if (!currentVerse.isEmpty()) {
                    result.add(currentVerse.toString().trim());
                }
                // Start a new verse with the current line
                currentVerse = new StringBuilder(line);
            } else {
                // Otherwise, append the current line to the ongoing verse
                currentVerse.append(" ").append(line);
            }
        }

        // Add the last verse to the result list
        if (!currentVerse.isEmpty()) {
            result.add(currentVerse.toString().trim());
        }

        return result;
    }

    private synchronized Document getDocument(String bookName) throws IOException {
        BibleSourceDocuments bibleSourceDocuments = new BibleSourceDocuments();
        Document document = bibleSourceDocuments.getDocumentForBook(bookName);
        if (document != null) {
            return document;
        }

        throw new RuntimeException("No document found for book " + bookName);
    }

    public Map<Integer, String> getChapterFromBook() {
        try {
            Document doc = getDocument(bookName);
            Elements chapter1 = doc.select("h2:matches(^" + chapterName + "$)");
            if (chapter1.isEmpty()) {
                chapter1 = doc.select(":matchesOwn(^" + chapterName + "$)");
            }

            if (chapter1.isEmpty()) {
                return Collections.emptyMap();
            }

            return getVersesPerChapterWithDiacritics(chapter1);
        } catch (IOException e) {
            Log.error("Failed to connect to book: ");
        } catch (NoSuchElementException e) {
            Log.errorf("Failed retrieve verses for chapter: %s", chapterName);
        }

        return null;
    }

    private Map<Integer, String> getVersesPerChapterWithDiacritics(Elements nextSibling) {
        // collect chapterElements underneath the capter including title
        List<String> chapterElements = collectAllElementsUnderneath(nextSibling);

        // filter our any other data apart from chapterElements themselves
        List<String> verses = chapterElements.stream().filter(verse -> verse.matches(VERSE_NUMBER_AND_TEXT_STRUCTURE_PATTERN)).toList();

        // stop if no elements
        if (verses.isEmpty()) {
            throw new NoSuchElementException();
        }

        return extractVersesNumbersAndText(verses);
    }

    public Map<Integer, String> extractVersesNumbersAndText(List<String> verses) {
        List<Tuple2<Integer, Integer>> problematicVerses = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < verses.size(); i++) {
            Tuple2<Integer, String> verseNoAndText = extractVerseNoAndText(verses.get(i));

            if (verseNoAndText != null) {
                if (verseNoAndText.getItem1() != i + 1) {
                    problematicVerses.add(Tuple2.of(verseNoAndText.getItem1(), (i + 1)));
                }
                map.put(i + 1, verseNoAndText.getItem2());
            }
        }

//        writeReport(problematicVerses);

        return map;
    }

    private void writeReport(List<Tuple2<Integer, Integer>> problematicVerses) {
        if (!problematicVerses.isEmpty()) {
            ConsoleReportWriterService consoleReportWriterService = new ConsoleReportWriterService(BibleStringUtils.removeDiacritics(bookName).replaceAll(" ", "-") + "migration-report-");
            consoleReportWriterService.writeLine("#####################");
            consoleReportWriterService.writeLine("Date '" + consoleReportWriterService.getDateNowFormatted() + "'");
            consoleReportWriterService.writeLine("Report for book '" + bookName + "'");
            consoleReportWriterService.writeLine("Chapter '" + chapterName + "'");
            consoleReportWriterService.writeLine("");

            problematicVerses.forEach(tuple -> {
                consoleReportWriterService.writeLine("#####");
                consoleReportWriterService.writeLine("Local HTML verseNo: '" + tuple.getItem1() + "' but sequence no is: '" + tuple.getItem2() + "' - Will add the sequence no in the db");
            });
            consoleReportWriterService.writeLine("##########-Report end-###########");
        }
    }

    private Tuple2<Integer, String> extractVerseNoAndText(String verse) {
        Pattern pattern = Pattern.compile(VERSE_NUMBER_AND_TEXT_STRUCTURE_PATTERN);
        Matcher verseMatcher = pattern.matcher(verse);

        if (verseMatcher.matches()) {
            Integer verseNumber = Integer.parseInt(verseMatcher.group(1));
            String verseText = BibleStringUtils.normalizeStringForDb(verseMatcher.group(2));
            return Tuple2.of(verseNumber, verseText);
        }

        return null;
    }

    private List<String> collectAllElementsUnderneath(Elements nextSibling) {
        List<String> chapterElements = new ArrayList<>();

        if (!nextSibling.isEmpty()) {
            Element element = moveToParentUntilSiblingsAreFound(nextSibling.getFirst());
            while (element != null && !element.text().startsWith(chapterKeyName)) {
                chapterElements.add(element.text());
                element = element.nextElementSibling();
            }
        }

        //inline verses
        return inlineVerses(normalizeHebrewText(chapterElements));
    }

    private List<String> normalizeHebrewText(List<String> input) {
        return input.stream()
                .map(BibleStringUtils::removeHebrewText)
                .map(a -> a.replaceAll("-{2,}", ""))
                .filter(a -> !a.trim().isEmpty())
                .flatMap(a -> unBindTwoStickVerses(a).stream())
                .map(this::inverseNumberIfRequired)
                .toList();
    }
    
    private String inverseNumberIfRequired(String string) {
        String INVERSE_NUMBER_PATTERN = "^\\s*\\.\\s*(\\d{1,3})\\s*(.*)$";
        Pattern r = Pattern.compile(INVERSE_NUMBER_PATTERN);
        Matcher m = r.matcher(string);
        if (m.find()) {
            String verseNo = m.group(1);
            String verseText = m.group(2);
            return verseNo+". "+verseText;
        }

        return string;
    }

    private List<String> unBindTwoStickVerses(String line) {
        String TWO_VERSES_STICK_PATTERN ="(\\d{1,3}\\s*\\.+.*\\D)(\\d{1,3}\\s*\\.\\s+.*)";
        Pattern r = Pattern.compile(TWO_VERSES_STICK_PATTERN);
        Matcher m = r.matcher(line);
        if (m.find()) {
            String verse1 = m.group(1);
            String verse2 = m.group(2);
            return List.of(verse1, verse2);
        }

        return List.of(line);
    }

    public List<String> inlineVerses(List<String> strings) {
        return inlineLostTexts(inlineLostNumbers(strings));
    }

    public List<String> inlineLostNumbers(List<String> strings) {
        List<String> result = new ArrayList<>();

        int i = 0;
        while (i < strings.size()) {
            String line = strings.get(i);
            if (line.matches("\\d+")) {
                result.add(line + strings.get(i + 1));
                i = i + 2;
                continue;
            }
            result.add(strings.get(i++));
        }

        return result;
    }

    private Element moveToParentUntilSiblingsAreFound(Element element) {
        while (element.nextElementSibling() == null) {
            element = (Element) element.parentNode();
        }

        return element.nextElementSibling();
    }
}
