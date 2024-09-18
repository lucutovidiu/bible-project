package ro.bible.maintanance.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.pojo.BookPojo;
import ro.bible.db.service.BookService;
import ro.bible.db.service.VerseService;
import ro.bible.filewriter.ReportWriter;
import ro.bible.util.BibleStringUtils;
import ro.bible.util.BibleUtil;
import ro.bible.util.FileUtil;
import ro.bible.yahwehtora.dto.BibleVerseDto;
import ro.bible.yahwehtora.dto.BookInfo;
import ro.bible.yahwehtora.service.ChapterExtractor;
import ro.bible.yahwehtora.service.MenuExtractor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ro.bible.db.service.BookService.PLACE_HOLDER_MSG;

@ApplicationScoped
public class BibleMigrationService {
    @Inject
    BookService bookService;
    @Inject
    VerseService verseService;
    @Inject
    BookReportingService bookReportingService;

    public void populateBookTableFromYahwehtora() {
        try {
            Log.infof("Running migrate books from Yahwehtora");
            // try extract all books from https://yahwehtora.ro/
            MenuExtractor.extractBibleMenus().forEach(bibleBookLink -> {
                Log.info("Check if book already exists in bookInfoList");
                Optional<BookInfo> bookInfoOptional = BibleUtil.getBookInfoList().stream().filter(book -> book.getBookName().equalsIgnoreCase(bibleBookLink.bookName())).findAny();

                if (!bibleBookLink.bookName().equalsIgnoreCase("Prefața")) {
                    Log.info("Update or create book");
                    if (bookInfoOptional.isEmpty()) {
                        Log.infof("Book: '%s' NOT found in bookInfoList", bibleBookLink.bookName());
                        BookInfo bookInfo = BookInfo.builder()
                                .bookName(bibleBookLink.bookName())
                                .bookOrder(0)
                                .abbreviation(PLACE_HOLDER_MSG)
                                .testament(PLACE_HOLDER_MSG)
                                .expChaptersCount(0)
                                .expTotalVerses(0)
                                .downloadUrl(bibleBookLink.url())
                                .build();
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

    public void populateBooksTableIfRequired() {
        List<BookInfo> bookInfoList = BibleUtil.getBookInfoList();
        if (bookService.getBookCount() < bookInfoList.size()) {
            bookInfoList.forEach(bookInfo -> {
                Log.infof("Update or create book: '%s'", bookInfo.getBookName());
                bookService.updateOrCreateBook(bookInfo);
            });
        }
    }

    public void migrateRequiredBooks() {
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
        Log.infof("Run vector update for full search capability!");
        verseService.updateSearchVector(bookPojo.getBookId());
        // todo there will need to think of a different way of generating reports
        /*
        Log.infof("Clean report files, Only leave top 3 most recent Files!");
        FileUtil.dropFilesApartFromMostRecent(ReportWriter.REPORT_BASE_PATH_FULL_REPORT,
                ReportWriter.REPORT_PRE_EXTENSION, 3);
        */
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
        Log.infof("Patching book: %s, chapter: %s", bookPojo.getName(), chapterNumber);
        ChapterExtractor chapterExtractor = new ChapterExtractor(bookPojo.getName(), bookPojo.chapterKey() + " " + chapterNumber, bookPojo.chapterKey());
        Map<Integer, String> chapterFromBook = chapterExtractor.getChapterFromBook();
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
