package ro.bible.yahwehtora.service;

import org.junit.jupiter.api.Test;
import ro.bible.util.BibleStringUtils;
import ro.bible.util.BibleUtil;
import ro.bible.util.FileUtil;
import ro.bible.yahwehtora.dto.BookInfo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Bidi;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class VersesExtractorTest {

    @Test
    public void extractVerses() throws IOException {
        ChapterExtractor chapterExtractor = new ChapterExtractor();
        Map<Integer, String> chapterFromBook = chapterExtractor.getChapterFromBook("Ioan", "https://yahwehtora.ro/ioan-yochanan", "Capitolul 1", "Capitolul");
        chapterFromBook.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach((entry) -> System.out.printf("%s. %s\n", entry.getKey(), entry.getValue()));

//         String currentVerse1="30. Toate lucrurile au fost create prin El; şi nimic din ce a fost creat, n-a fost creat fără El.";
//         String currentVerse="3. Toate lucrurile au fost create prin El; şi nimic din ce a fost creat, n-a fost creat fără El.4. În El* era viaţa, şi** viaţa era lumina oamenilor.";
//         String currentVerse1="42. Şi l-a adus la  Yașua.  Yașua l-a privit şi i-a zis: „Tu eşti Simon, fiul lui Iona; tu* te vei chema Chifa” [care tălmăcit înseamnă Petru].  Tora - Pericopa 7 - Vayetze 28 : 10 - 32 : 3 Haftarah - Osea / Hoshea 12 : 13 - 14 : 10 Legămîntul Înnoit - Ioan / Yochanan 1 : 43 - 51";
//        final String VERSE_PATTERN = "\\d{1,3}\\.?\\s*.+\\w{3,}.+";
//        final String VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE = "^("+VERSE_PATTERN+")("+VERSE_PATTERN+")$";
//
//        Pattern pattern = Pattern.compile(VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE);
//        Matcher verseMatcher = pattern.matcher(currentVerse1);
//        if(verseMatcher.find()) {
//            System.out.println(verseMatcher.group(1));
//            System.out.println(verseMatcher.group(2));
//        } else {
//            System.out.println(currentVerse1.toString());
//        }


//        System.out.println(forceLeftToRight(" 1.יהוה"));
//        String VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE = "(.*)(\\.\\s*\\d{1,3}\\s*.*)";
//        String verse = "8. Însă Cain a zis fratelui său Abel: „Haidem săieşim la cîmp.” Dar pe cînd erau la cîmp, Cain saridicat împotriva fratelui său Abel şi la omorît.Pedeapsa lui Cain . 9 YaHWeH a zis lui Cain: „Unde este frateletău Abel?” El a răspuns: „Nu ştiu. Sunt eupăzitorul fratelui meu?”";
//
//        Pattern pattern = Pattern.compile(VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE);
//        Matcher verseMatcher = pattern.matcher(verse);
//
//        if(verseMatcher.find()) {
//            System.out.println(verseMatcher.group(1));
//            System.out.println(verseMatcher.group(2));
//        }
    }

    @Test
    public void test_downloadAllRequiredHtmlSources() {
        BibleSourceDocuments bibleSourceDocuments = new BibleSourceDocuments();
        bibleSourceDocuments.downloadAllRequiredHtmlSources();
    }

    @Test
    public void test_Path() {
        FileUtil.createFolderIfNotExists("src/main/resources/ovi");
        FileUtil.createFileIfNotExists("src/main/resources/ovi/test.htmly");
    }

    @Test
    public void create_BookPaths() {
        BibleUtil.bookInfoList.forEach(book -> {
            String bookBegining = book.bookName().split(" ")[0];
            if(bookBegining.matches("\\d+")) {
                bookBegining= book.bookName().split(" ")[0]+book.bookName().split(" ")[1];
            }
            System.out.printf("booksPath.add(new BookLocalPaths(\"%s\", \"src/main/resources/bible-source-documents/%s\",\"%s.html\"));\n", book.bookName(),
                    BibleStringUtils.removeDiacritics(bookBegining.toLowerCase().replaceAll("[.-]", "")),
                    BibleStringUtils.removeDiacritics(bookBegining.toLowerCase().replaceAll("[.-]", "")));
        });
    }
}
