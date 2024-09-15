package ro.bible.yahwehtora.service;

import org.junit.jupiter.api.Test;
import ro.bible.util.BibleStringUtils;
import ro.bible.util.BibleUtil;
import ro.bible.util.FileUtil;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class VersesExtractorTest {

    @Test
    public void extractVerses() {
        ChapterExtractor chapterExtractor = new ChapterExtractor("1 Macabei", "Capitolul 3", "Capitolul");
        Map<Integer, String> chapterFromBook = chapterExtractor.getChapterFromBook();
        chapterFromBook.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach((entry) -> System.out.printf("%s. %s\n", entry.getKey(), entry.getValue()));
    }

    @Test
    public void inlineVerses() {
        String string = "17. „În* zilele de pe urmă, zice  YaHWeH, voi** turna din Duhul Meu peste orice Om!; feciorii voştri şi fetele♦ voastre vor proroci, tinerii voştri vor avea vedenii, şi bătrînii voştri vor visa vise! [ Fap 2:17.Evr.: Ba-sar : pentru . Oricine-i Om or Adam ]";
        String string1 = ".20 Şi învăţaţi ucenicii* să păzească tot ce v-am poruncit. Şi iată că Eu sunt cu voi în toate zilele, pînă la sfîrşitul veacului.” HalelluYa. [ Vers.19 : manuscrisurile tradiționale au forma :numele Tatalui, Fiului si al Duhului Sfint ]";

        String INVERSE_NUMBER_PATTERN = "^\\s*\\.\\s*(\\d{1,3})\\s*(.*)$";
        Pattern r = Pattern.compile(INVERSE_NUMBER_PATTERN);
        Matcher m = r.matcher(string);
        if (m.find()) {
            String verseNo = m.group(1);
            String verseText = m.group(2);
            System.out.println(verseNo+". "+verseText);
        }



    }

    @Test
    public void test_versePattern() throws IOException {
        String verse = "23. De acolo s-a suit la Beer-Şeba.    24.  YaHWeH i S-a arătat chiar în noaptea aceea şi i-a zis: „Eu* sunt Eylohim al tatălui tău, Avraam; nu te teme**, căci Eu sunt♦ cu tine; te voi binecuvînta şi îţi voi înmulţi sămînţa, din pricina robului Meu, Avraam.”  ";

        String VERSE_PATTERN_PART1 = "\\d{1,3}\\s*\\.?\\s*.+\\w{3,}.+\\D+";
        String VERSE_PATTERN_PART2 = "\\d{1,3}\\s*\\.?\\s*.+\\w{3,}.+";
//        String VERSE_PATTERN = "\\d{1,3}\\.?\\s*[^\\d]+";
        String VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE = "^(" + VERSE_PATTERN_PART1 + ")(" + VERSE_PATTERN_PART2 + ")$";
        Pattern pattern = Pattern.compile(VERSE_STRUCTURE_PATTERN_2_VERSES_IN_ONE_LINE);
        Matcher verseMatcher = pattern.matcher(verse);
        if (verseMatcher.find()) {
            String group1 = verseMatcher.group(1);
            String group2 = verseMatcher.group(2);
            System.out.println(group1);
            System.out.println(group2);
        }
    }

    @Test
    public void test_createAllRequiredHtmlLocalSources() {
        BibleSourceDocuments bibleSourceDocuments = new BibleSourceDocuments();
        bibleSourceDocuments.createAllRequiredHtmlLocalSources();
    }

    @Test
    public void test_getDocumentForBook() {
        BibleSourceDocuments bibleSourceDocuments = new BibleSourceDocuments();
        System.out.println(bibleSourceDocuments.getDocumentForBook("menu").title());
    }

    @Test
    public void test_Path() {
//        FileUtil.createFolderIfNotExists("src/main/resources/ovi");
//        FileUtil.createFileIfNotExists("src/main/resources/ovi/test.htmly");
    }

    @Test
    public void test_FileUtil() {
        System.out.println(FileUtil.doesFileExists("src/main/resources/bible-source-documents/apocalipsa/apocalipsa.html"));
    }

    @Test
    public void create_BookPaths() {
        BibleUtil.bookInfoList.forEach(book -> {
            String bookBegining = book.bookName().split(" ")[0];
            if (bookBegining.matches("\\d+")) {
                bookBegining = book.bookName().split(" ")[0] + book.bookName().split(" ")[1];
            }
            System.out.printf("booksPath.add(new BookLocalPaths(\"%s\", \"src/main/resources/bible-source-documents/%s\",\"%s.html\"));\n", book.bookName(),
                    BibleStringUtils.removeDiacritics(bookBegining.toLowerCase().replaceAll("[.-]", "")),
                    BibleStringUtils.removeDiacritics(bookBegining.toLowerCase().replaceAll("[.-]", "")));
        });
    }
}
