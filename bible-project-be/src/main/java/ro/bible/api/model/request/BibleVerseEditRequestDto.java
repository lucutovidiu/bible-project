package ro.bible.api.model.request;

import lombok.Data;

@Data
public class BibleVerseEditRequestDto {
    private String abbreviation;
    private String textWithDiacritics;
    private String bookName;
    private long bookId;
    private int chapterNumber;
    private int verseNumber;
}
