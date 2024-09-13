package ro.bible;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.service.BookService;
import ro.bible.maintanance.service.BookMaintenanceService;
import ro.bible.maintanance.service.BookReportingService;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
public class StartupListener {
    @Inject
    BookService bookService;
    @Inject
    BookMaintenanceService bookMaintenanceService;
    @Inject
    BookReportingService bookReportingService;

    AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    @Startup
    void onStart() throws IOException, InterruptedException {
//        bookMaintenanceService.buildMissingChapters();

//        bookMaintenanceService.updateBookLinks();
//        Log.info("Db population is starting");
//        MenuExtractor.extractBibleMenus()
//                .forEach(bibleBookLink -> {
//                    VersesExtractor versesExtractor = new VersesExtractor(
//                            bibleBookLink.bookName().trim()
//                                    .equalsIgnoreCase("Psalmii - Tehillim") ? "Psalmul" : "Capitolul");
//
//                    versesExtractor.setBibleService(bibleService);
//                    versesExtractor
//                            .extractBibleFromWebAndWriteToDB(bibleBookLink);
//                });
//        Log.info("Db population is finished");
    }

//    @Scheduled(every = "10s")
        // Every hour, on the hour
    void runDailyTask() {
        if (atomicBoolean.get()) {
            atomicBoolean.set(false);
//            bookMaintenanceService.patchAllBooks();
//            bookReportingService.runBookReport();
        }
        System.out.println("not runnign...");
    }
}
