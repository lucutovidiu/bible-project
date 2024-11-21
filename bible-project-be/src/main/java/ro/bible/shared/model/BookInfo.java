package ro.bible.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.logging.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bible.persistence.model.BookPojo;

import java.io.File;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInfo {
    private int bookOrder;
    private String bookName;
    private String bookHebrewName;
    private int expChaptersCount;
    private int expTotalVerses;
    private String abbreviation;
    private String testament;
    private String downloadUrl;
    private String baseFolderPath;
    private String storedFileName;

    public BookInfo updateBookInfo(BookPojo bookPojo) {
        Log.infof("Updating Book Info: %s", bookPojo.getName());
        this.bookOrder = bookPojo.getBookOrder();
        this.bookName = bookPojo.getName();
        this.expChaptersCount = bookPojo.getExpChaptersCount();
        this.expTotalVerses = bookPojo.getExpTotalVerses();
        this.abbreviation = bookPojo.getAbbreviation();
        this.testament = bookPojo.getTestament();
        this.bookHebrewName = bookPojo.getHebrewName();

        return this;
    }

    @JsonIgnore
    public String getFilePath() {
        return baseFolderPath + File.separator + storedFileName;
    }
}

