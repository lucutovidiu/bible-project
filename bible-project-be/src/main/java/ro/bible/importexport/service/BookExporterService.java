package ro.bible.importexport.service;

public interface BookExporterService {

    void exportAllBooks();

    void exportBook(String bookName);
}
