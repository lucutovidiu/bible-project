package ro.bible.db.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VersePojo {
    private int verseNumber;
    private String text;
    private String textWithDiacritics;
    private ChapterPojo chapter;
}
