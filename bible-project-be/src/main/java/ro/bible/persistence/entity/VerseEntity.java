package ro.bible.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.bible.persistence.model.VersePojo;

@Getter
@Setter
@Entity
@Table(name = "verses")
public class VerseEntity extends BaseEntity {

    @Column(name = "verse_number")
    private int verseNumber;
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;
    @Column(name = "text_diacritics", columnDefinition = "TEXT")
    private String textWithDiacritics;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", referencedColumnName = "id")
    private ChapterEntity chapter;

    public VersePojo getVerseExclChapterPojo() {
        return VersePojo.builder()
                .verseNumber(verseNumber)
                .text(text)
                .textWithDiacritics(textWithDiacritics)
                .build();
    }

    public VersePojo getFullVersePojo() {
        return VersePojo.builder()
                .verseNumber(verseNumber)
                .text(text)
                .textWithDiacritics(textWithDiacritics)
                .chapter(chapter.getChapterExclVersePojo())
                .build();
    }
}
