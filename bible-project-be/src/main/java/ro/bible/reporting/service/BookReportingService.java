package ro.bible.reporting.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import ro.bible.persistence.model.BookPojo;
import ro.bible.persistence.model.ChapterPojo;
import ro.bible.persistence.model.VersePojo;
import ro.bible.persistence.service.BookService;
import ro.bible.persistence.service.ChapterService;
import ro.bible.persistence.service.VerseService;
import ro.bible.localfiles.util.BibleUtil;

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
    @ConfigProperty(name = "quarkus.profile")
    String activeProfile;

    public void runBookReport() {
        Log.infof("Running Book Report...");
        List<BookPojo> bookPojos = bookService.getAllBooks().stream()
                .toList();
        buildReport(bookPojos);
    }

    private void buildReport(List<BookPojo> books) {
        // Report Header
        ConsoleReportWriterService consoleReportWriterService = new ConsoleReportWriterService(activeProfile);
        consoleReportWriterService.writeLine("######################");
        consoleReportWriterService.writeLine("Begin Report date: " + LocalDateTime.now().format(DATE_TIME_FORMATTER));
        consoleReportWriterService.writeLine("");
        // missing books report
        verifyMissingBooks(books, consoleReportWriterService);
        consoleReportWriterService.writeLine("");
        consoleReportWriterService.writeLine("######################");
        // Report per book
        books.forEach(book -> reportPerBook(book, consoleReportWriterService));
        // Report Footer
        consoleReportWriterService.writeLine("End Report date" + LocalDateTime.now().format(DATE_TIME_FORMATTER));
        consoleReportWriterService.writeLine("######################");
    }

    private void reportPerBook(BookPojo book, ConsoleReportWriterService consoleReportWriterService) {
        consoleReportWriterService.writeLine("Book Name: " + book.getName());
        consoleReportWriterService.writeLine("");
        boolean isChaptersCountOk = verifyChaptersCount(book, consoleReportWriterService);
        if (!isChaptersCountOk) {
            checkForMissingChapter(book, consoleReportWriterService);
        }
        consoleReportWriterService.writeLine("");
        verifyVersesCount(book, consoleReportWriterService);
        consoleReportWriterService.writeLine("");
        verifyMissingVerses(book, consoleReportWriterService);
        consoleReportWriterService.writeLine("");
        consoleReportWriterService.writeLine("######################");
    }

    private void checkForMissingChapter(BookPojo book, ConsoleReportWriterService consoleReportWriterService) {
        consoleReportWriterService.writeLine("########");
        consoleReportWriterService.writeLine("Missing chapters: ");
        Integer expChaptersCount = book.getExpChaptersCount();
        List<Integer> missingChapter = new ArrayList<>();
        for (int i = 1; i <= expChaptersCount; i++) {
            Optional<ChapterPojo> chapterPojo = chapterService.getChapterByBookAndChapterNumber(book.getBookId(), i);
            if (chapterPojo.isEmpty()) {
                missingChapter.add(i);
            }
        }
        consoleReportWriterService.writeLine(missingChapter.toString());
        consoleReportWriterService.writeLine("########");
    }

    private void verifyVersesCount(BookPojo book, ConsoleReportWriterService consoleReportWriterService) {
        consoleReportWriterService.writeLine("Verses verification result:");
        consoleReportWriterService.writeLine("Expected verses Count: " + book.getExpTotalVerses());
        long actualCount = verseService.getVerseCountByBookId(book.getBookId());
        consoleReportWriterService.writeLine("Actual verses Count: " + actualCount);
        consoleReportWriterService.writeLine("Pass: " + (actualCount == book.getExpTotalVerses()));
    }

    private void verifyMissingVerses(BookPojo book, ConsoleReportWriterService consoleReportWriterService) {
        consoleReportWriterService.writeLine("Missing verses Report: ");
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
                consoleReportWriterService.writeLine("Chapter: " + chapterNo+
                        ", "+ "Found Missing verses: [" +
                        missingVersesList.stream().map(String::valueOf).collect(Collectors.joining(","))+"]");
            }
        });
    }

    private boolean verifyChaptersCount(BookPojo book, ConsoleReportWriterService consoleReportWriterService) {
        consoleReportWriterService.writeLine("Chapter verification result:");
        consoleReportWriterService.writeLine("Expected chapter Count: " + book.getExpChaptersCount());
        long actualCount = chapterService.getChapterCountByBookId(book.getBookId());
        consoleReportWriterService.writeLine("Actual chapter Count: " + actualCount);
        consoleReportWriterService.writeLine("Pass: " + (actualCount == book.getExpChaptersCount()));

        return actualCount == book.getExpChaptersCount();
    }

    private void verifyMissingBooks(List<BookPojo> books, ConsoleReportWriterService consoleReportWriterService) {
        consoleReportWriterService.writeLine("Chapter missing books result:");
        AtomicBoolean pass = new AtomicBoolean(true);
        BibleUtil.getBookInfoList().forEach(bookInfo -> {
            if (books.stream().filter(bi -> bi.getName().equals(bookInfo.getBookName())).findAny().isEmpty()) {
                pass.set(false);
                consoleReportWriterService.writeLine("Missing book: " + bookInfo.getBookName());
            }
        });
        consoleReportWriterService.writeLine("Pass: " + pass.get());
    }
}
