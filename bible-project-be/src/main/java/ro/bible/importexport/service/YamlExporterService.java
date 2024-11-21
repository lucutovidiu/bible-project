package ro.bible.importexport.service;

import ro.bible.persistence.model.BookPojo;

public interface YamlExporterService {

    void exportYaml(BookPojo bookPojo, String bookContent);
}
