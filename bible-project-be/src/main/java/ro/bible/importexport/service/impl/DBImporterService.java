package ro.bible.importexport.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.importexport.service.ImporterService;
import ro.bible.importexport.service.YamlExporterService;
import ro.bible.importexport.util.BookPojoYamlPathUtil;
import ro.bible.persistence.model.BookPojo;
import ro.bible.persistence.service.BookService;
import ro.bible.shared.model.BibleVerseDto;
import ro.bible.shared.model.BookInfo;
import ro.bible.shared.util.FileUtil;

@ApplicationScoped
public class DBImporterService implements ImporterService {
    @Inject
    BookService bookService;

    @Override
    public void updateOrCreateBook(BookInfo bookInfo, BookPojo bookPojo) {
        bookInfo.updateBookInfo(bookPojo);
        bookService.updateOrCreateBook(bookInfo);
        patchVersesToDb(bookPojo);
    }

    @Override
    public void importBookTableOnly(BookInfo bookInfo) {
        bookService.updateOrCreateBook(bookInfo);
    }

    private void patchVersesToDb(BookPojo bookPojo) {
        Log.infof("Building bible verse pojo for book: ", bookPojo.getName());
        bookPojo.getChapterPojo().forEach(chapterPojo ->
                chapterPojo.getVersePojo().forEach(versePojo ->
                        patchVersesToDb(BibleVerseDto.builder()
                                .bookName(bookPojo.getName())
                                .chapterNumer(chapterPojo.getNumber())
                                .verseNumber(versePojo.getVerseNumber())
                                .verseText(versePojo.getText())
                                .verseTextWithDiacritics(versePojo.getTextWithDiacritics())
                                .build())
                )
        );
    }

    private void patchVersesToDb(BibleVerseDto bibleVerseDto) {
        Log.infof("Patching to DB Book Name: %s, chapter: %s", bibleVerseDto.getBookName(), bibleVerseDto.getChapterNumer());
        bookService.writeBibleVerse(bibleVerseDto);
    }
}
