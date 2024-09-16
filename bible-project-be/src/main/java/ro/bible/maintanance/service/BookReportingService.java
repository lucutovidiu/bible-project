package ro.bible.maintanance.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.pojo.BookPojo;
import ro.bible.db.pojo.ChapterPojo;
import ro.bible.db.pojo.VersePojo;
import ro.bible.db.service.BookService;
import ro.bible.db.service.ChapterService;
import ro.bible.db.service.VerseService;
import ro.bible.filewriter.ReportWriter;
import ro.bible.util.BibleUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookReportingService {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
    @Inject
    BookService bookService;
    @Inject
    ChapterService chapterService;
    @Inject
    VerseService verseService;

    public void runBookReport() {
        Log.infof("Running Book Report...");
        List<BookPojo> bookPojos = bookService.getAllBooks().stream()
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
        verifyMissingVerses(book, reportWriter);
        reportWriter.writeLine("");
        reportWriter.writeLine("######################");
    }

    private void checkForMissingChapter(BookPojo book, ReportWriter reportWriter) {
        reportWriter.writeLine("########");
        reportWriter.writeLine("Missing chapters: ");
        Integer expChaptersCount = book.getExpChaptersCount();
        List<Integer> missingChapter = new ArrayList<>();
        for (int i = 1; i <= expChaptersCount; i++) {
            Optional<ChapterPojo> chapterPojo = chapterService.getChapterByBookAndChapterNumber(book.getBookId(), i);
            if (chapterPojo.isEmpty()) {
                missingChapter.add(i);
            }
        }
        reportWriter.writeLine(missingChapter.toString());
        reportWriter.writeLine("########");
    }

    private void verifyVersesCount(BookPojo book, ReportWriter reportWriter) {
        reportWriter.writeLine("Verses verification result:");
        reportWriter.writeLine("Expected verses Count: " + book.getExpTotalVerses());
        long actualCount = verseService.getVerseCountByBookId(book.getBookId());
        reportWriter.writeLine("Actual verses Count: " + actualCount);
        reportWriter.writeLine("Pass: " + (actualCount == book.getExpTotalVerses()));
    }

    private void verifyMissingVerses(BookPojo book, ReportWriter reportWriter) {
        reportWriter.writeLine("Missing verses Report: ");
        Map<Integer, List<Integer>> missingVerses = new HashMap<>();
        for(int i=1; i<=book.getExpChaptersCount(); i++) {
            Optional<ChapterPojo> chapterWithVerses = chapterService.getChapterByBookAndChapterNumberInclVerses(book.getBookId(), i);
            // handle when there is no chapter at all
            if(chapterWithVerses.isEmpty()) {
                missingVerses.put(i, List.of());
                continue;
            }
            // check if verses are in order for all the verses available
            ChapterPojo chapterPojo = chapterWithVerses.get();
            List<VersePojo> versePojo = chapterPojo.getVersePojo();
            int actualNoOfVerses = versePojo.stream()
                    .max(Comparator.comparing(VersePojo::getVerseNumber))
                    .map(VersePojo::getVerseNumber)
                    .orElse(versePojo.size());

            List<Integer> missing = new ArrayList<>();
            for(int j=1; j<=actualNoOfVerses; j++) {
                int finalJ = j;
                if(versePojo.stream().filter(verse -> verse.getVerseNumber() == finalJ)
                        .findFirst().isEmpty()){
                    missing.add(j);
                }
            }

            missingVerses.put(i, missing);
        }

        missingVerses.forEach((chapterNo, missingVersesList) -> {
            if (!missingVersesList.isEmpty()) {
                reportWriter.writeLine("Chapter: " + chapterNo+
                        ", "+ "Found Missing verses: [" +
                        missingVersesList.stream().map(String::valueOf).collect(Collectors.joining(","))+"]");
            }
        });
    }

    private boolean verifyChaptersCount(BookPojo book, ReportWriter reportWriter) {
        reportWriter.writeLine("Chapter verification result:");
        reportWriter.writeLine("Expected chapter Count: " + book.getExpChaptersCount());
        long actualCount = chapterService.getChapterCountByBookId(book.getBookId());
        reportWriter.writeLine("Actual chapter Count: " + actualCount);
        reportWriter.writeLine("Pass: " + (actualCount == book.getExpChaptersCount()));

        return actualCount == book.getExpChaptersCount();
    }

    private void verifyMissingBooks(List<BookPojo> books, ReportWriter reportWriter) {
        reportWriter.writeLine("Chapter missing books result:");
        AtomicBoolean pass = new AtomicBoolean(true);
        BibleUtil.getBookInfoList().forEach(bookInfo -> {
            if (books.stream().filter(bi -> bi.getName().equals(bookInfo.getBookName())).findAny().isEmpty()) {
                pass.set(false);
                reportWriter.writeLine("Missing book: " + bookInfo.getBookName());
            }
        });
        reportWriter.writeLine("Pass: " + pass.get());
    }
}
