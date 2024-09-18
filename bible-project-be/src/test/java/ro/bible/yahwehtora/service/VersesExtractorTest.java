package ro.bible.yahwehtora.service;

import org.junit.jupiter.api.Test;
import ro.bible.util.BibleStringUtils;
import ro.bible.util.BibleUtil;
import ro.bible.util.ConvertUtil;
import ro.bible.util.FileUtil;
import ro.bible.yahwehtora.dto.BookInfo;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class VersesExtractorTest {

//    @Test
//    public void extractVerses() {
//        ChapterExtractor chapterExtractor = new ChapterExtractor("1 Macabei", "Capitolul 3", "Capitolul");
//        Map<Integer, String> chapterFromBook = chapterExtractor.getChapterFromBook();
//        chapterFromBook.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .forEach((entry) -> System.out.printf("%s. %s\n", entry.getKey(), entry.getValue()));
//    }
//
//    @Test
//    public void inlineVerses() {
//        String string = "17. „În* zilele de pe urmă, zice  YaHWeH, voi** turna din Duhul Meu peste orice Om!; feciorii voştri şi fetele♦ voastre vor proroci, tinerii voştri vor avea vedenii, şi bătrînii voştri vor visa vise! [ Fap 2:17.Evr.: Ba-sar : pentru . Oricine-i Om or Adam ]";
//        String string1 = ".20 Şi învăţaţi ucenicii* să păzească tot ce v-am poruncit. Şi iată că Eu sunt cu voi în toate zilele, pînă la sfîrşitul veacului.” HalelluYa. [ Vers.19 : manuscrisurile tradiționale au forma :numele Tatalui, Fiului si al Duhului Sfint ]";
//
//        String INVERSE_NUMBER_PATTERN = "^\\s*\\.\\s*(\\d{1,3})\\s*(.*)$";
//        Pattern r = Pattern.compile(INVERSE_NUMBER_PATTERN);
//        Matcher m = r.matcher(string);
//        if (m.find()) {
//            String verseNo = m.group(1);
//            String verseText = m.group(2);
//            System.out.println(verseNo + ". " + verseText);
//        }
//
//
//    }

//    @Test
//    public void test_versePattern() throws IOException {
//        String verse = "23. De acolo s-a suit la Beer-Şeba.    24.  YaHWeH i S-a arătat chiar în noaptea aceea şi i-a zis: „Eu* sunt Eylohim al tatălui tău, Avraam; nu te teme**, căci Eu sunt♦ cu tine; te voi binecuvînta şi îţi voi înmulţi sămînţa, din pricina robului Meu, Avraam.”  ";
//
//        String VERSE_PATTERN_PART1 = "\\d{1,3}\\s*\\.?\\s*.+\\w{3,}.+\\D+";
//        String VERSE_PATTERN_PART2 = "\\d{1,3}\\s*\\.?\\s*.+\\w{3,}.+";
//        String VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE = "^(" + VERSE_PATTERN_PART1 + ")(" + VERSE_PATTERN_PART2 + ")$";
//        Pattern pattern = Pattern.compile(VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE);
//        Matcher verseMatcher = pattern.matcher(verse);
//        if (verseMatcher.find()) {
//            String group1 = verseMatcher.group(1);
//            String group2 = verseMatcher.group(2);
//            System.out.println(group1);
//            System.out.println(group2);
//        }
//    }

//    @Test
//    public void test_createAllRequiredHtmlLocalSources() {
//        BibleSourceDocuments bibleSourceDocuments = new BibleSourceDocuments();
//        bibleSourceDocuments.createAllRequiredHtmlLocalSources();
//    }

//    @Test
//    public void test_getDocumentForBook() {
//        BibleSourceDocuments bibleSourceDocuments = new BibleSourceDocuments();
//        System.out.println(bibleSourceDocuments.getDocumentForBook("menu").title());
//    }

//    @Test
//    public void test_Path() {
//        Optional<String> uri = FileUtil.getFileFromClasspath("reports/full-report/full-report-data-17-09-2024-23-17-45.txt");
//        if (uri.isPresent()) {
//            System.out.println(uri.get());
//        }
//    }
//
//    @Test
//    public void test_FileUtil() {
//        System.out.println(FileUtil.doesFileExists("src/main/resources/bible-source-documents/apocalipsa/apocalipsa.html"));
//    }

//    @Test
//    public void create_BookPaths() {
//        BibleUtil.getBookInfoList().forEach(book -> {
//            String bookBegining = book.getBookName().split(" ")[0];
//            if (bookBegining.matches("\\d+")) {
//                bookBegining = book.getBookName().split(" ")[0] + book.getBookName().split(" ")[1];
//            }
//            System.out.printf("booksPath.add(new BookLocalPaths(\"%s\", \"src/main/resources/bible-source-documents/%s\",\"%s.html\"));\n", book.getBookName(),
//                    BibleStringUtils.removeDiacritics(bookBegining.toLowerCase().replaceAll("[.-]", "")),
//                    BibleStringUtils.removeDiacritics(bookBegining.toLowerCase().replaceAll("[.-]", "")));
//        });
//    }

//    @Test
//    public void create_menu_from_bookInfoList() {
////        System.out.println(BibleUtil.getBookInfoList());
////        BibleUtil.getBookInfoList()
////                .forEach(book -> {
////                    System.out.println(book.toJson());
////        });
//        List<BookInfo> list = BibleUtil.getBookInfoList();
////                .stream()
////                .map(book -> {
////                    BookLocalPaths bookLocalPaths = BibleSourceDocuments.booksPath.stream()
////                            .filter(path -> path.getBookName().equals(book.getBookName())).findFirst().get();
////                    book.setBaseFolderPath(bookLocalPaths.getBookBaseFolderPath());
////                    book.setStoredFileName(bookLocalPaths.getBookFileName());
////                    return book;
////                }).toList();
//        System.out.println(ConvertUtil.toJson(list));
//    }
//
//
//    @Test
//    public void dropFilesApartFromMostRecent() {
//        String basePath = "src/main/resources/reports";
//        String fileStartingWith = "full-report-data";
//        int limit = 3;
//
//        FileUtil.dropFilesApartFromMostRecent(basePath, fileStartingWith, limit);
//    }
}
