package ro.bible;

import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.service.BookService;
import ro.bible.maintanance.service.BibleMigrationService;
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
    BibleMigrationService bibleMigrationService;

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

    @Scheduled(every = "5s")
    void runDailyTask() {
//        if (atomicBoolean.get()) {
//            atomicBoolean.set(false);
            bibleMigrationService.migrateRequiredBooks();
//            bibleMigrationService.migrateBooksFromYahwehtora();
//        }
//        System.out.println("not runnign...");
    }
}
