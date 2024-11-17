package ro.bible.importexport.service;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.bible.persistence.model.BookPojo;
import ro.bible.persistence.service.BookService;

import java.util.Optional;

@ApplicationScoped
public class YamlBookWriterService {

    private static final Logger log = LoggerFactory.getLogger(YamlBookWriterService.class);
    @Inject
    BookService bookService;

    @Scheduled(every =  "5m")
    public void testBookWriter() {
        writeBook("Ioan – Yochanan");
    }

    public void writeBook(String bookName) {
        Optional<BookPojo> entireBookByName = bookService.getEntireBookByName(bookName);
        if(entireBookByName.isPresent()) {
            BookPojo bookPojo = entireBookByName.get();
            Log.info(bookPojo);
        }
    }
}
