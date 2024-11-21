package ro.bible.migration.service;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.config.ImportExportConfig;
import ro.bible.importexport.service.BookExporterService;
import ro.bible.importexport.service.BookImporterService;
import ro.bible.persistence.service.BookService;

@ApplicationScoped
public class BibleExporterService {

    @Inject
    BookExporterService bookExporterService;
    @Inject
    ImportExportConfig importExportConfig;
    @Inject
    BookImporterService bookImporterService;
    @Inject
    BookService bookService;

    @Scheduled(every = "5m")
    public void exportBooks() {
        if (importExportConfig.exportEnabled()) {
            Log.info("Exporting Books");
            bookService.getAllBooks().forEach(bookPojo ->
                    bookExporterService.exportBook(bookPojo.getName()));
        } else {
            Log.info("Skipping Exporting books");
        }
    }

    @Scheduled(every = "5m")
    public void importBooks() {
        if (importExportConfig.importEnabled()) {
            Log.info("Importing Books");
            bookImporterService.importBooks();
        } else {
            Log.info("Skipping Importing books");
        }
    }
}
