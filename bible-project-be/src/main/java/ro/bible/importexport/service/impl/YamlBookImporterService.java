package ro.bible.importexport.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.importexport.service.BookImporterService;
import ro.bible.importexport.service.ImporterService;
import ro.bible.importexport.util.BookPojoYamlPathUtil;
import ro.bible.localfiles.util.BibleUtil;
import ro.bible.persistence.model.BookPojo;
import ro.bible.shared.model.BookInfo;
import ro.bible.shared.model.BookTestament;
import ro.bible.shared.service.YamlMapperService;
import ro.bible.shared.util.FileUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@ApplicationScoped
public class YamlBookImporterService implements BookImporterService {

    @Inject
    ImporterService importerService;
    @Inject
    YamlMapperService yamlMapperService;

    @Override
    public void importAllBooks() {
        Log.info("Started importing Books");
        BibleUtil.getBookInfoList()
                .forEach(this::importBook);
    }

    @Override
    public void patchAllBooksBulk() {
        Instant start = Instant.now();
        Log.info("Started importing Books in Bulk");
        BibleUtil.getBookInfoList()
                .forEach(this::patchBookBulk);
        Instant end = Instant.now();
        Log.infof("Finished importing Books in Bulk in: '%s' seconds", Duration.between(start, end).getSeconds());
    }

    @Override
    public void importByBookTestament(BookTestament bookTestament) {
        Log.infof("Started importing Book Testament: %s", bookTestament.getBookTestament());
        BibleUtil.getBookInfoList()
                .stream()
                .filter(bookInfo -> bookInfo.getTestament().equals(bookTestament.getBookTestament()))
                .forEach(this::importBook);
    }

    @Override
    public void importByBookName(String bookName) {
        Log.infof("Started importing Book name: %s", bookName);
        BibleUtil.getBookInfoList()
                .stream()
                .filter(bookInfo -> bookInfo.getBookName().equals(bookName))
                .forEach(this::importBook);
    }

    @Override
    public void importBooksMetadataOnly() {
        Log.info("Started importing Books Table Only");
        BibleUtil.getBookInfoList()
                .forEach(this::importBooksMetadataOnly);
    }

    private void importBooksMetadataOnly(BookInfo bookInfo) {
        Log.infof("Importing Book: %s", bookInfo.toString());
        retrieveBookPojo(bookInfo)
                .flatMap(this::convertToYaml)
                .ifPresent(bookPojo -> importerService.importBookTableOnly(bookInfo.updateBookInfo(bookPojo)));
    }

    private void importBook(BookInfo bookInfo) {
        Log.infof("Importing Book: %s", bookInfo.toString());
        retrieveBookPojo(bookInfo)
                .flatMap(this::convertToYaml)
                .ifPresentOrElse(bookPojo -> importerService.updateOrCreateBook(bookInfo, bookPojo), () -> {
                    Log.errorf("Book NOT found: %s, testament: %s", bookInfo.getBookName(), bookInfo.getTestament());
                });
        Log.infof("Finished Importing Book: %s", bookInfo.toString());
    }

    private void patchBookBulk(BookInfo bookInfo) {
        Log.infof("Pathing Book bulk: %s", bookInfo.toString());
        retrieveBookPojo(bookInfo)
                .flatMap(this::convertToYaml)
                .ifPresentOrElse(bookPojo -> importerService.patchBookBulkVersesAndChapters(bookInfo, bookPojo),
                        () -> Log.errorf("Book NOT found: %s, testament: %s", bookInfo.getBookName(), bookInfo.getTestament()));
        Log.infof("Finished Pathing bulk Book: %s", bookInfo.toString());
    }

    private Optional<BookPojo> convertToYaml(String bookYamlString) {
        return yamlMapperService.yamlToObject(bookYamlString, BookPojo.class);
    }

    private Optional<String> retrieveBookPojo(BookInfo bookInfo) {
        BookPojoYamlPathUtil bookPojoYamlPathUtil = new BookPojoYamlPathUtil(bookInfo.getTestament(), bookInfo.getAbbreviation());
        bookPojoYamlPathUtil.setResourceFolder(FileUtil.CLASSPATH_BIBLE_BASE_FOLDER_NAME);

        return FileUtil.getFileFromClasspath(bookPojoYamlPathUtil.getYamlFullBaseFile());
    }

}
