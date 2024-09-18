package ro.bible;

import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.service.BookService;
import ro.bible.maintanance.service.BibleMigrationService;
import ro.bible.maintanance.service.BookMaintenanceService;

@ApplicationScoped
public class StartupListener {
    @Inject
    BookService bookService;
    @Inject
    BookMaintenanceService bookMaintenanceService;
    @Inject
    BibleMigrationService bibleMigrationService;


    @Startup
    void onStart() {
        bibleMigrationService.populateBooksTableIfRequired();
    }

    @Scheduled(every = "8h")
    void runDailyTask() {
//        if (atomicBoolean.get()) {
//            atomicBoolean.set(false);
            bibleMigrationService.migrateRequiredBooks();
//            bibleMigrationService.migrateBooksFromYahwehtora();
//        }
//        System.out.println("not runnign...");
    }
}
