package ro.bible.resourses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bible.db.pojo.BookPojo;

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

    public BibleBookInfoDto setFromBookPojo(BookPojo bookPojo) {
        this.bookId = bookPojo.getBookId();
        this.name = bookPojo.getName();
        this.abbreviation = bookPojo.getAbbreviation();
        this.testament = bookPojo.getTestament();

        return this;
    }

    public BibleBookInfoDto setChaptersCount(long verseCount) {
        this.chaptersCount = verseCount;

        return this;
    }
}
