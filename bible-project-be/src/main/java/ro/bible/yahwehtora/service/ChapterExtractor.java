package ro.bible.yahwehtora.service;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.bible.util.BibleStringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class ChapterExtractor {
    public static final String VERSE_NUMBER_AND_TEXT_STRUCTURE_PATTERN = "(\\d{1,3})\\.?\\s*(.+\\w{3,}.+)";
    //    private static final String VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE = "(\\d{1,3}\\s*\\.?.+)(\\d{1,3}\\s*\\.?.+)";
    private static final String VERSE_PATTERN = "\\d{1,3}\\.?\\s*.+\\w{3,}.+";
    private static final String VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE = "^(" + VERSE_PATTERN + ")(" + VERSE_PATTERN + ")$";
    ConcurrentHashMap<String, Document> documents = new ConcurrentHashMap<>();

    private void addValidOnlyInlineVerse(List<String> inlineVerses, String currentVerse) {
        if (currentVerse.matches(VERSE_PATTERN) && !currentVerse.startsWith("0")) {
            inlineVerses.add(currentVerse);
        }
    }

    private synchronized Document getDocument(String bookName, String bookUrl) throws IOException {
        Document document = documents.get(bookName);
        if (document != null) {
            return document;
        }
        Document doc = Jsoup.connect(bookUrl).get();
        documents.put(bookName, doc);

        return doc;
    }

    public Map<Integer, String> getChapterFromBook(String bookName, String bookUrl, String chapterName, String chapterKeyName) {
        try {
            Document doc = getDocument(bookName, bookUrl);
            Elements chapter1 = doc.select("h2:matches(^" + chapterName + "$)");
            if (chapter1.size() == 0) {
                chapter1 = doc.select(":matchesOwn(^" + chapterName + "$)");
            }

            return getVersesPerChapterWithDiacritics(chapter1, chapterKeyName);
        } catch (IOException e) {
            Log.error("Failed to connect to book: " + bookUrl, e);
        } catch (NoSuchElementException e) {
            Log.errorf("Failed retrieve verses for chapter: %s, bookUrl: %s", chapterName, bookUrl);
        }

        return null;
    }

    private Map<Integer, String> getVersesPerChapterWithDiacritics(Elements nextSibling, String chapterKeyName) {
        // collect chapterElements underneath the capter including title
        List<String> chapterElements = collectAllElementsUnderneath(nextSibling, chapterKeyName);

        // filter our any other data apart from chapterElements themselves
        List<String> verses = chapterElements.stream().filter(verse -> verse.matches(VERSE_NUMBER_AND_TEXT_STRUCTURE_PATTERN)).toList();

        // stop if no elements
        if (verses.isEmpty()) {
            throw new NoSuchElementException();
        }

        return extractVersesNumbersAndText(verses);
    }

    private Map<Integer, String> extractVersesNumbersAndText(List<String> verses) {
        Map<Integer, String> map = new HashMap<>();

        for (String verse : verses) {
            Tuple2<Integer, String> verseNoAndText = extractVerseNoAndText(verse);

            if (verseNoAndText != null) {
                map.put(verseNoAndText.getItem1(), verseNoAndText.getItem2());
            }
        }

        return map;
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

    private List<String> collectAllElementsUnderneath(Elements nextSibling, String chapterKeyName) {
        List<String> chapterElements = new ArrayList<>();

        if (!nextSibling.isEmpty()) {
            Element element = moveToParentUntilSiblingsAreFound(nextSibling.getFirst());
            while (element != null && !element.text().startsWith(chapterKeyName)) {
                chapterElements.add(element.text());
                element = element.nextElementSibling();
            }
        }

        // remove and normalaize hebrew text in list
        List<String> list = chapterElements.stream().map(BibleStringUtils::removeHebrewText).toList();

        //inline verses
        return inlineVerses(list);
    }

    private List<String> inlineVerses(List<String> chapterElements) {
        List<String> inlineVerses = new ArrayList<>();
        StringBuilder currentVerse = new StringBuilder();
        for (String string : chapterElements) {
            if (string.matches(VERSE_NUMBER_AND_TEXT_STRUCTURE_PATTERN)) {
                // split if 2 verses
                splitIfTwoChaptersStickTogether(currentVerse, inlineVerses);
                currentVerse = new StringBuilder();
                currentVerse.append(string);
            } else {
                currentVerse.append(" ");
                currentVerse.append(string);
            }
        }
        addValidOnlyInlineVerse(inlineVerses, currentVerse.toString().trim());

        return inlineVerses;
    }

    private void splitIfTwoChaptersStickTogether(StringBuilder currentVerse, List<String> inlineVerses) {
        Pattern pattern = Pattern.compile(VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE);
        Matcher verseMatcher = pattern.matcher(currentVerse);
        if (verseMatcher.find()) {
            Tuple2<Integer, String> verse1 = extractVerseNoAndText(verseMatcher.group(1));
            Tuple2<Integer, String> verse2 = extractVerseNoAndText(verseMatcher.group(2));
            if (verse1 != null && verse2 != null) {
                if (verse2.getItem1() == (verse1.getItem1() + 1)) {
                    addValidOnlyInlineVerse(inlineVerses, verseMatcher.group(1));
                    addValidOnlyInlineVerse(inlineVerses, verseMatcher.group(2));
                }
            }
        } else {
            addValidOnlyInlineVerse(inlineVerses, currentVerse.toString().trim());
        }
    }

    private Element moveToParentUntilSiblingsAreFound(Element element) {
        while (element.nextElementSibling() == null) {
            element = (Element) element.parentNode();
        }

        return element.nextElementSibling();
    }
}
