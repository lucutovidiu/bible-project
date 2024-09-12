package ro.bible;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.service.BibleService;

import java.io.IOException;

@ApplicationScoped
public class StartupListener {
    @Inject
    BibleService bibleService;

    @Startup
    void onStart() throws IOException, InterruptedException {
//        Log.info("Db population is starting");
//        TimeUnit.SECONDS.sleep(1);
//        MenuExtractor.extractBibleMenus()
//                .forEach(bibleBookLink -> {
//                    VersesExtractor versesExtractor = new VersesExtractor(
//                            bibleBookLink.bookTitle().trim()
//                                    .equalsIgnoreCase("Psalmii - Tehillim") ? "Psalmul" : "Capitolul");
//
//                    versesExtractor.setBibleService(bibleService);
//                    versesExtractor
//                            .extractBibleFromWebAndWriteToDB(bibleBookLink);
//                });
//        Log.info("Db population is finished");
    }
}
