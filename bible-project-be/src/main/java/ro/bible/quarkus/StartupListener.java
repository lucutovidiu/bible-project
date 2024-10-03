package ro.bible.quarkus;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.context.api.ManagedExecutorConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.Fallback;
import ro.bible.db.service.BookService;
import ro.bible.maintanance.service.BibleMigrationService;
import ro.bible.maintanance.service.BookMaintenanceService;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@ApplicationScoped
public class StartupListener {
    @Inject
    BookService bookService;
    @Inject
    BookMaintenanceService bookMaintenanceService;
    @Inject
    BibleMigrationService bibleMigrationService;
    AtomicInteger counter = new AtomicInteger(1);

    @Inject
    ManagedExecutor executorService;

    @Startup
    void onStart() {
        bibleMigrationService.populateBooksTableIfRequired();
    }

    @Scheduled(every = "5m")
    void runDailyTask() {
        int andIncrement = counter.getAndIncrement();
        test(andIncrement);
        System.out.println("Adding new task: " + andIncrement);
//        CustomExecutorService.executorService.submit(() -> bibleMigrationService.migrateRequiredBooks());
    }

    @Asynchronous
//    @Fallback(fallbackMethod = "onOverload")
    @Bulkhead(value = 2, waitingTaskQueue = 3)
    public Future<Void> test(int id) {
        int took = new Random().nextInt(10) + 11;
        try {
            TimeUnit.SECONDS.sleep(took);
        } catch (InterruptedException e) {
            Log.errorf("Error in sleep: %s", e.getMessage());
        }
        System.out.println("Finished execution of task " + id + " took: " + took);
        return CompletableFuture.completedFuture(null);
    }

    public Future<Void> onOverload(int id) {
        System.out.println("Please call me next time later id: "+ id);
        return CompletableFuture.completedFuture(null);

    }


}
