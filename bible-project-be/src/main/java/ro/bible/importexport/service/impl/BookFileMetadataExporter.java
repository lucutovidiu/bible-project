package ro.bible.importexport.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.persistence.model.BookPojo;
import ro.bible.persistence.service.BookService;
import ro.bible.shared.model.BookInfo;
import ro.bible.shared.util.ConvertUtil;
import ro.bible.shared.util.FileUtil;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class BookFileMetadataExporter {

    @Inject
    BookService bookService;

    public void exportBookMetadata() {
        Log.info("Exporting All Books Metadata");
        exportBookMetadata(bookService.getAllBooks()
                .stream()
                .map(BookPojo::toBookInfo)
                .sorted(Comparator.comparing(BookInfo::getBookOrder))
                .toList());
    }

    private void exportBookMetadata(List<BookInfo> bookInfo) {
        Log.infof("Exporting Book Metadata size: '%s'", bookInfo.size());
        String json = ConvertUtil.toJson(bookInfo);
        FileUtil.writeContentToFile(FileUtil.FULLPATH_BOOK_TABLE_FILE_PATH, json);
    }
}
