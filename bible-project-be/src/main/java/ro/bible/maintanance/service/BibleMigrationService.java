package ro.bible.maintanance.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.pojo.BookPojo;
import ro.bible.db.service.BookService;
import ro.bible.util.BibleStringUtils;
import ro.bible.util.BibleUtil;
import ro.bible.yahwehtora.dto.BibleVerseDto;
import ro.bible.yahwehtora.dto.BookInfo;
import ro.bible.yahwehtora.service.ChapterExtractor;
import ro.bible.yahwehtora.service.MenuExtractor;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static ro.bible.db.service.BookService.PLACE_HOLDER_MSG;

@ApplicationScoped
public class BibleMigrationService {
    @Inject
    BookService bookService;
    @Inject
    BookReportingService bookReportingService;
    @Inject
    ChapterExtractor chapterExtractor;

    public void migrateBooksFromYahwehtora() {
        try {
            Log.infof("Running migrate books from Yahwehtora");
            // try extract all books from https://yahwehtora.ro/
            MenuExtractor.extractBibleMenus().forEach(bibleBookLink -> {
                Log.info("Check if book already exists in bookInfoList");
                Optional<BookInfo> bookInfoOptional = BibleUtil.bookInfoList.stream().filter(book -> book.bookName().equalsIgnoreCase(bibleBookLink.bookName())).findAny();

                if (!bibleBookLink.bookName().equalsIgnoreCase("Prefața")) {
                    Log.info("Update or create book");
                    if (bookInfoOptional.isEmpty()) {
                        Log.infof("Book: '%s' NOT found in bookInfoList", bibleBookLink.bookName());
                        BookInfo bookInfo = new BookInfo(bibleBookLink.bookName(),0, PLACE_HOLDER_MSG, PLACE_HOLDER_MSG, 0, 0, bibleBookLink.url());
                        bookService.updateOrCreateBook(bookInfo);
                    } else {
                        Log.infof("Book: '%s' found and updating", bibleBookLink.bookName());
                        bookService.updateOrCreateBook(bookInfoOptional.get());
                    }
                }
            });
        } catch (IOException e) {
            Log.error("Could not download menu", e);
        }
    }

    public void migrateRequiredBooks() {
        Log.info("Build a pojoOptional of required books to be updated");
        Log.info("Running migrate required books");
        Optional<BookPojo> pojoOptional = bookService.getAllBooks().stream()
                .filter(BookPojo::getRequiresUpdate)
                .toList().stream().findFirst();

        Log.info("Run book migration update");
        pojoOptional.ifPresent(this::beginMigration);
        Log.info("Migration update finished!");
    }

    private void beginMigration(BookPojo bookPojo) {
        if (!bookService.isBoolAlreadyTakenIfNotMarketItAsTaken(bookPojo.getBookId())) {
            initMigration(bookPojo);
            postMigration(bookPojo);
        }
    }

    private void initMigration(BookPojo bookPojo) {
        Log.infof("Running migration for book: %s", bookPojo.getName());
        patchWholeBookByName(bookPojo.getName());
    }

    private void postMigration(BookPojo bookPojo) {
        Log.infof("Post migration for book: %s", bookPojo.getName());
        Log.infof("Set update NOT required");
        bookService.setBookUpdatedStatus(bookPojo.getName(), false);
        Log.infof("Run report for new updates!");
        bookReportingService.runBookReport();
    }

    public void patchWholeBookByName(String bookName) {
        Optional<BookPojo> bookPojo = bookService.getOrCreateBookPojo(bookName);
        if (bookPojo.isPresent()) {
            for (int i = 1; i <= bookPojo.get().getExpChaptersCount(); i++) {
                patchChapter(bookPojo.get(), i);
            }
        }
    }

    public void patchChapter(BookPojo bookPojo, int chapterNumber) {
        String downloadedLink = bookPojo.getDownloadedLink();
        Log.infof("Patching book: %s, chapter: %s, url: %s", bookPojo.getName(), chapterNumber, downloadedLink);

        Map<Integer, String> chapterFromBook = chapterExtractor.getChapterFromBook(bookPojo.getName(), downloadedLink, bookPojo.chapterKey() + " " + chapterNumber, bookPojo.chapterKey());
        if (chapterFromBook != null) {
            patchVersesToDb(bookPojo, chapterNumber, chapterFromBook);
        } else {
            Log.errorf("Chapter %s not found for download, bookName: %s", chapterNumber, bookPojo.getName());
        }
    }

    public void patchVersesToDb(BookPojo bookPojo, int chapterNumer, Map<Integer, String> verseTextMap) {
        verseTextMap.forEach((verseNumber, verseText) -> {
            Log.infof("Patching to DB Book Name: %s, chapter: %s", bookPojo.getName(), chapterNumer);
            bookService.writeBibleVerse(
                    BibleVerseDto.builder()
                            .bookName(bookPojo.getName())
                            .chapterNumer(chapterNumer)
                            .verseNumber(verseNumber)
                            .verseText(BibleStringUtils.removeSpecialChars(BibleStringUtils.removeDiacritics(verseText)))
                            .verseTextWithDiacritics(BibleStringUtils.removeSpecialChars(verseText))
                            .build());
        });
    }
}
