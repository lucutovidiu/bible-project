package ro.bible.importexport.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.importexport.service.BookImporterService;
import ro.bible.importexport.service.ImporterService;
import ro.bible.importexport.service.YamlExporterService;
import ro.bible.importexport.util.BookPojoYamlPathUtil;
import ro.bible.localfiles.util.BibleUtil;
import ro.bible.persistence.model.BookPojo;
import ro.bible.persistence.service.BookService;
import ro.bible.shared.model.BibleVerseDto;
import ro.bible.shared.model.BookInfo;
import ro.bible.shared.service.YamlMapperService;
import ro.bible.shared.util.FileUtil;

import java.util.Optional;

@ApplicationScoped
public class YamlBookImporterService implements BookImporterService {

    @Inject
    ImporterService importerService;
    @Inject
    YamlMapperService yamlMapperService;

    @Override
    public void importBooks() {
        Log.info("Started importing Books");
        BibleUtil.getBookInfoList()
                .forEach(this::importBook);
    }

    private void importBook(BookInfo bookInfo) {
        Log.infof("Importing Book: %s", bookInfo.toString());
        retrieveBookPojo(bookInfo)
                .flatMap(this::convertToYaml)
                .ifPresent(bookPojo -> importerService.updateOrCreateBook(bookInfo, bookPojo));
    }

    private Optional<BookPojo> convertToYaml(String bookYamlString) {
        return yamlMapperService.yamlToObject(bookYamlString, BookPojo.class);
    }

    private Optional<String> retrieveBookPojo(BookInfo bookInfo) {
        BookPojoYamlPathUtil bookPojoYamlPathUtil = new BookPojoYamlPathUtil(bookInfo.getTestament(), bookInfo.getAbbreviation());
        bookPojoYamlPathUtil.setResourceFolder(FileUtil.BIBLE_RESOURCE_FOLDER);

        return FileUtil.getFileFromClasspath(bookPojoYamlPathUtil.getYamlFullBaseFile());
    }

}
