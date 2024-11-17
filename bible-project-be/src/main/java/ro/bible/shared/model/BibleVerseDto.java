package ro.bible.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class BibleVerseDto {

    private String bookName;
    private int chapterNumer;
    private int verseNumber;
    private String verseText;
    private String verseTextWithDiacritics;
}
