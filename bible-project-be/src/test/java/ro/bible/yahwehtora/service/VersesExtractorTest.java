package ro.bible.yahwehtora.service;

import org.junit.jupiter.api.Test;
import ro.bible.util.BibleUtil;


class VersesExtractorTest {
    @Test
    public void extractVerses() {
//        ChapterExtractor chapterExtractor = new ChapterExtractor("Capitolul");
//        chapterExtractor.getChapterFromBook("https://yahwehtora.ro/exod-shemoth", "Capitolul 22");
        BibleUtil.bookInfoList.forEach(
                bookInfo -> {
                    System.out.printf("bookInfoList.add(new BookInfo(\"%s\", \"%s\", \"%s\", %s, %s));\n",
            bookInfo.bookName(), bookInfo.abbreviation(), bookInfo.testament(),bookInfo.chaptersCount(), bookInfo.totalVerses()
        );
                }
        );
    }
}
