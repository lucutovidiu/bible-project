package ro.bible.migration.service;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.config.ImportExportConfig;
import ro.bible.importexport.service.BookExporterService;
import ro.bible.importexport.service.BookImporterService;
import ro.bible.importexport.service.impl.BookFileMetadataExporter;
import ro.bible.persistence.service.BookService;
import ro.bible.persistence.service.VerseService;

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
    @Inject
    BookFileMetadataExporter bookFileMetadataExporter;
    @Inject
    VerseService verseService;

    @Scheduled(every = "5m")
    public void exportBooksMetadata() {
        if (importExportConfig.fileMetadataEnabled()) {
            Log.info("Exporting Books Metadata");
            bookFileMetadataExporter.exportBookMetadata();
        } else {
            Log.info("Skipping Exporting books metadata");
        }
    }

    @Scheduled(every = "5m")
    public void exportBooks() {
        if (importExportConfig.exportEnabled()) {
            Log.info("Exporting Books");
            bookService.getAllBooks().forEach(bookPojo ->
                    bookExporterService.exportBook(bookPojo.getName()));
//            bookService.getAllBooks().forEach(bookPojo ->
//                    verseService.updateSearchVector(bookPojo.getBookId()));
        } else {
            Log.info("Skipping Exporting books");
        }
    }

    @Scheduled(every = "5m")
    public void importBooks() {
        if (importExportConfig.importEnabled()) {
            Log.info("Importing Books");
//            bookImporterService.importByBookName("1 Ioan");
//            bookImporterService.importByBookTestament(BookTestament.APOCRYPHA);
//            bookImporterService.importAllBooks();
            bookImporterService.patchAllBooksBulk();
//            bookImporterService.importBooksMetadataOnly();
        } else {
            Log.info("Skipping Importing books");
        }
    }
}
