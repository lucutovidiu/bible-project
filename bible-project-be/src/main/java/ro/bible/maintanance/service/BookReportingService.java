package ro.bible.maintanance.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.entity.BookEntity;
import ro.bible.db.entity.ChapterEntity;
import ro.bible.db.pojo.BookPojo;
import ro.bible.db.repo.BookRepository;
import ro.bible.db.repo.ChapterRepository;
import ro.bible.db.repo.VerseRepository;
import ro.bible.filewriter.ReportWriter;
import ro.bible.util.BibleUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
public class BookReportingService {
    @Inject
    BookRepository bookRepository;
    @Inject
    ChapterRepository chapterRepository;
    @Inject
    VerseRepository verseRepository;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");

    public void runBookReport() {
        List<BookPojo> bookPojos = bookRepository.findAll().stream()
                .map(BookEntity::getBookPojoNoChapters)
                .toList();
        buildReport(bookPojos);
    }

    private void buildReport(List<BookPojo> books) {
        // Report Header
        ReportWriter reportWriter = new ReportWriter();
        reportWriter.writeLine("######################");
        reportWriter.writeLine("Begin Report date: " + LocalDateTime.now().format(DATE_TIME_FORMATTER));
        reportWriter.writeLine("");
        // missing books report
        verifyMissingBooks(books, reportWriter);
        reportWriter.writeLine("");
        reportWriter.writeLine("######################");
        // Report per book
        books.forEach(book -> reportPerBook(book, reportWriter));
        // Report Footer
        reportWriter.writeLine("End Report date" + LocalDateTime.now().format(DATE_TIME_FORMATTER));
        reportWriter.writeLine("######################");
    }

    private void reportPerBook(BookPojo book, ReportWriter reportWriter) {
        reportWriter.writeLine("Book Name: " + book.getName());
        reportWriter.writeLine("");
        boolean isChaptersCountOk = verifyChaptersCount(book, reportWriter);
        if (!isChaptersCountOk) {
            checkForMissingChapter(book, reportWriter);
        }
        reportWriter.writeLine("");
        verifyVersesCount(book, reportWriter);
        reportWriter.writeLine("");
        reportWriter.writeLine("######################");
    }

    private void checkForMissingChapter(BookPojo book, ReportWriter reportWriter) {
        reportWriter.writeLine("########");
        reportWriter.writeLine("Missing chapters: ");
        Integer expChaptersCount = book.getExpChaptersCount();
        List<Integer> missingChapter = new ArrayList<>();
        for(int i = 1; i<= expChaptersCount; i++) {
            Optional<ChapterEntity> chapterEntity = chapterRepository.getChapterByBookAndChapterNumber(i, book.getBookId());
            if(chapterEntity.isEmpty()) {
                missingChapter.add(i);
            }
        }
        reportWriter.writeLine(missingChapter.toString());
        reportWriter.writeLine("########");
    }

    private void verifyVersesCount(BookPojo book, ReportWriter reportWriter) {
        Log.infof("verifyVersesCount for book: ", book.getName());
        reportWriter.writeLine("Verses verification result:");
        reportWriter.writeLine("Expected verses Count: " + book.getExpTotalVerses());
        long actualCount = verseRepository.getVerseCountByBookId(book.getBookId());
        reportWriter.writeLine("Actual verses Count: " + actualCount);
        reportWriter.writeLine("Pass: " + (actualCount == book.getExpTotalVerses()));
    }

    private boolean verifyChaptersCount(BookPojo book, ReportWriter reportWriter) {
        Log.infof("verifyChaptersCount for book: ", book.getName());
        reportWriter.writeLine("Chapter verification result:");
        reportWriter.writeLine("Expected chapter Count: " + book.getExpChaptersCount());
        long actualCount = chapterRepository.getChapterCountByBookId(book.getBookId());
        reportWriter.writeLine("Actual chapter Count: " + actualCount);
        reportWriter.writeLine("Pass: " + (actualCount == book.getExpChaptersCount()));

        return actualCount == book.getExpChaptersCount();
    }

    private void verifyMissingBooks(List<BookPojo> books, ReportWriter reportWriter) {
        reportWriter.writeLine("Chapter missing books result:");
        AtomicBoolean pass = new AtomicBoolean(true);
        BibleUtil.bookInfoList.forEach(bookInfo -> {
            if (books.stream().filter(bi -> bi.getName().equals(bookInfo.bookName())).findAny().isEmpty()) {
                pass.set(false);
                reportWriter.writeLine("Missing book: " + bookInfo.bookName());
            }
        });
        reportWriter.writeLine("Pass: " + pass.get());
    }
}
