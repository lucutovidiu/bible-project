package ro.bible.yahwehtora.service;

import io.quarkus.logging.Log;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.bible.db.service.BookService;
import ro.bible.filewriter.BibleFileWriter;
import ro.bible.util.BibleStringUtils;
import ro.bible.yahwehtora.dto.BibleBookLink;
import ro.bible.yahwehtora.dto.BibleVerseDto;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Data
public class VersesExtractor {

    private final String chapterKeyName;

    private BookService bookService;

    public VersesExtractor(String chapterKeyName) {
        this.chapterKeyName = chapterKeyName;
    }

    public void extractVersesFromBook(BibleBookLink bibleBookLink) {
        BibleFileWriter bibleFileWriter = new BibleFileWriter(bibleBookLink.bookName());

        try {
            Document doc = Jsoup.connect(bibleBookLink.url()).get();
            Elements chapters = doc.select("h2:contains(" + chapterKeyName + ")");

            System.out.println("writeHtmlPageItself: " + bibleBookLink.bookName());
            bibleFileWriter.writeHtmlPageItself(doc.html());
            for (Element chapter : chapters) {
                String chapterTitle = chapter.text();
                List<Element> verses = getVerses(chapter.nextElementSibling());

                bibleFileWriter.writeVerses(chapterTitle, mapVersesToList(getVersesByNo(verses)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void extractBibleFromWebAndWriteToDB(BibleBookLink bibleBookLink) {
        try {
            Document doc = Jsoup.connect(bibleBookLink.url()).get();
            Elements chapters = doc.select("h2:contains(" + chapterKeyName + ")");
            for (Element chapter : chapters) {
                String chapterTitle = chapter.text();
                List<Element> verses = getVerses(chapter.nextElementSibling());

                mapVersesElementToText(getVersesByNo(verses)).forEach((verseNumber, verseText) -> {
                    try {
                        Log.infof("At book: %s, and chapter: %s, verseNumber: %s", bibleBookLink.bookName(), chapterTitle, verseNumber);

                        int chapterNumer = Integer.parseInt(chapterTitle.split(" ")[1]);
                        bookService.writeBibleVerse(
                                BibleVerseDto.builder()
                                        .bookName(bibleBookLink.bookName())
                                        .chapterNumer(chapterNumer)
                                        .verseNumber(verseNumber)
                                        .verseText(BibleStringUtils.removeSpecialChars(BibleStringUtils.removeDiacritics(verseText)))
                                        .verseTextWithDiacritics(BibleStringUtils.removeSpecialChars(verseText))
                                        .build());
                    } catch (NumberFormatException e) {
                        Log.errorf("ERROR! At book: %s, and chapter: %s", bibleBookLink.bookName(), chapterTitle);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> mapVersesToList(Map<Integer, List<Element>> verses) {
        return verses.values().stream()
                .map(elements -> elements.stream()
                        .map(verse ->
                                normaliseString(removeReOrderNumbers(removeHebrewText(verse.text()))))
                        .collect(Collectors.joining(" "))
                )
                .collect(toList());

    }

    private Map<Integer, String> mapVersesElementToText(Map<Integer, List<Element>> verses) {

        return verses.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> elementsListToString(entry.getValue()))
                );
    }

    private String elementsListToString(List<Element> elements) {
        String text = elements.stream()
                .map(element -> normaliseString(removeReOrderNumbers(removeHebrewText(element.text()))))
                .collect(Collectors.joining(" "));
        return extractTextWithoutNumbers(text);
    }

    private Map<Integer, List<Element>> getVersesByNo(List<Element> verses) {
        Map<Integer, List<Element>> map = new HashMap<>();
        int keyToWrite = 1;
        for (Element verse : verses) {
            String text = removeReOrderNumbers(removeHebrewText(verse.text()));
            if (text.matches("(\\d){0,2}\\.\\s*(.*)") && text.trim().length() > 2) {
                try {
                    keyToWrite = Integer.parseInt(text.substring(0, text.indexOf(".")).trim());
                } catch (NumberFormatException e) {
                    Log.errorf("ERROR! Verse Problem: %s, verseNo: %s", text, keyToWrite);
                }
            }
            map.compute(keyToWrite, (key, val) -> {
                if (val == null) {
                    val = new ArrayList<>();
                    val.add(verse);
                } else {
                    val.add(verse);
                }
                return val;
            });
        }

        return map;
    }

    private String extractTextWithoutNumbers(String input) {
        String regex = "(\\d{0,3})\\.\\s*(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            // Group 2 captures the text after the number, dot, and space
            return matcher.group(2);
        } else {
            return input;
        }
    }

    private List<Element> getVerses(Element nextSibling) {
        List<Element> verses = new ArrayList<>();
        while (nextSibling != null) {
            if (nextSibling.tagName().equalsIgnoreCase("h2") && nextSibling.text().contains(chapterKeyName)) {
                break;
            }

            if (nextSibling.tagName().equalsIgnoreCase("b") && !nextSibling.text().trim().isEmpty()) {
                verses.add(nextSibling);
            }

            nextSibling = nextSibling.nextElementSibling();
        }

        return verses;
    }

    private String removeHebrewText(String text) {
        return text.replaceAll("[\\u0590-\\u05FF]", "").trim();
    }

    private String normaliseString(String text) {
        return text.replaceAll("\\R+", " ")
                .replaceAll("-", "")
                .replaceAll(" {2,}", " ")
                .trim();
    }

    private String removeReOrderNumbers(String text) {
        try {
            if (text.startsWith(".") && text.trim().length() > 2) {
                String trim = text.substring(1).trim();
                String verseNo = trim.substring(0, trim.indexOf(" ")).trim();
                return verseNo + "." + trim.substring(trim.indexOf(" "));
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.err.printf("error text: '%s'", text);
        }
        return text;
    }
}
