package ro.bible.yahwehtora.service;

import io.quarkus.logging.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.bible.util.BibleStringUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChapterExtractor {
    private final String verseStructure = "(\\d{0,3})\\.\\s*(.*)";
    private final String chapterKeyName;

    public ChapterExtractor(String chapterKeyName) {
        this.chapterKeyName = chapterKeyName;
    }

    public Map<Integer, String> getChapterFromBook(String bookUrl, String chapterName) {
        try {
            Document doc = Jsoup.connect(bookUrl).get();
            Elements chapter1 = doc.select("h2:matches(^" + chapterName + "$)");

            Map<Integer, String> versesPerChapter = getVersesPerChapterWithDiacritics(chapter1);

            versesPerChapter.forEach((k, v) -> System.out.println(k + ": " + v));

            return versesPerChapter;
        } catch (IOException e) {
            Log.error("Failed to connect to book: " + bookUrl, e);
        } catch (NoSuchElementException e) {
            Log.errorf("Failed retrieve verses for chapter: %s, bookUrl: %s", chapterName, bookUrl);
        }

        return null;
    }

    private Map<Integer, String> getVersesPerChapterWithDiacritics(Elements nextSibling) {
        // collect chapterElements underneath the capter including title
        List<String> chapterElements = collectAllElementsUnderneath(nextSibling);

        // filter our any other data apart from chapterElements themselves
        List<String> verses = chapterElements.stream().filter(verse -> verse.matches(verseStructure)).toList();

        // stop if no elements
        if (verses.isEmpty()) {
            throw new NoSuchElementException();
        }

        return extractVersesNumbersAndText(verses);
    }

    private Map<Integer, String> extractVersesNumbersAndText(List<String> verses) {
        Map<Integer, String> map = new HashMap<>();

        for (String verse : verses) {
            Pattern pattern = Pattern.compile(verseStructure);
            Matcher verseMatcher = pattern.matcher(verse);

            if (verseMatcher.matches()) {
                int verseNumber = Integer.parseInt(verseMatcher.group(1));
                String verseText = BibleStringUtils.normalizeStringForDb(verseMatcher.group(2));
                map.put(verseNumber, verseText);
            }
        }

        return map;
    }

    private List<String> collectAllElementsUnderneath(Elements nextSibling) {
        List<String> chapterElements = new ArrayList<>();

        if (!nextSibling.isEmpty()) {
            Element element = nextSibling.getFirst().nextElementSibling();
            while (element != null && !element.text().startsWith(this.chapterKeyName)) {
                chapterElements.add(element.text());
                element = element.nextElementSibling();
            }
        }

        return chapterElements;
    }
}
