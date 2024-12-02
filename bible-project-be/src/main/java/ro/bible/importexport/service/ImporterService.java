
package ro.bible.importexport.service;

import ro.bible.persistence.model.BookPojo;
import ro.bible.shared.model.BookInfo;

public interface ImporterService {

    void updateOrCreateBook(BookInfo bookInfo, BookPojo bookPojo);

    void patchBookBulkVersesAndChapters(BookInfo bookInfo, BookPojo bookPojo);

    void importBookTableOnly(BookInfo bookInfo);
}
