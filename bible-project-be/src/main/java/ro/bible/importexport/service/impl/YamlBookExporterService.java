package ro.bible.importexport.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.importexport.service.BookExporterService;
import ro.bible.importexport.service.YamlExporterService;
import ro.bible.persistence.model.BookPojo;
import ro.bible.persistence.service.BookService;
import ro.bible.shared.service.YamlMapperService;

import java.util.Optional;

@ApplicationScoped
public class YamlBookExporterService implements BookExporterService {
    @Inject
    BookService bookService;
    @Inject
    YamlMapperService yamlMapperService;
    @Inject
    YamlExporterService yamlExporterService;

    @Override
    public void exportAllBooks() {
        Log.info("Exporting All Books");
        bookService.getAllBooks().forEach(bookPojo -> exportBook(bookPojo.getName()));
    }

    @Override
    public void exportBook(String bookName) {
        Optional<BookPojo> entireBookByName = bookService.getEntireBookByName(bookName);
        if (entireBookByName.isPresent()) {
            BookPojo bookPojo = entireBookByName.get();
            convertToYaml(bookPojo)
                    .ifPresentOrElse(bookContent -> exportYamlBook(bookPojo, bookContent), () ->
                            Log.errorf("Book: '%s', could not be converted", bookName)
                    );
        } else {
            Log.errorf("Book: '%s', not found in the database", bookName);
        }
    }

    private Optional<String> convertToYaml(BookPojo bookPojo) {
        return yamlMapperService.objectToString(bookPojo);
    }

    private void exportYamlBook(BookPojo bookPojo, String bookContent) {
        yamlExporterService.exportYaml(bookPojo, bookContent);
    }

}
