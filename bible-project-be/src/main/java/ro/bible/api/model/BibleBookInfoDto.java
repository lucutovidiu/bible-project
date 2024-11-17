package ro.bible.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bible.persistence.model.BookPojo;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BibleBookInfoDto {
    private long bookId;
    private String name;
    private String abbreviation;
    private String testament;
    private long chaptersCount;
    private int bookOrder;

    public BibleBookInfoDto setFromBookPojo(BookPojo bookPojo) {
        this.bookId = bookPojo.getBookId();
        this.name = bookPojo.getName();
        this.abbreviation = bookPojo.getAbbreviation();
        this.testament = bookPojo.getTestament();
        this.bookOrder = bookPojo.getBookOrder();

        return this;
    }

    public BibleBookInfoDto setChaptersCount(long verseCount) {
        this.chaptersCount = verseCount;

        return this;
    }
}
