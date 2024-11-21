package ro.bible.importexport.service;

import ro.bible.shared.model.BookTestament;

public interface BookImporterService {

    void importBooks();

    void importByBookTestament(BookTestament bookTestament);

    void importByBookName(String bookName);

    // just books table update
    void importBooksMetadataOnly();
}
