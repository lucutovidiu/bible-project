package ro.bible.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.bible.persistence.model.ChapterPojo;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "chapters")
public class ChapterEntity extends BaseEntity {

    @Column(name = "chapter_number")
    private int number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "chapter")
    private List<VerseEntity> verses = new ArrayList<>();

    public ChapterPojo getChapterExclVersePojo() {
        return ChapterPojo.builder()
                .number(number)
                .book(book.getBookPojoNoChapters())
                .build();
    }

    public ChapterPojo getFullChapterPojo() {
        return ChapterPojo.builder()
                .number(number)
                .book(book.getBookPojoNoChapters())
                .versePojo(verses.stream().map(VerseEntity::getVerseExclChapterPojo).toList())
                .build();
    }

    public ChapterPojo getChapterExclBookPojo() {
        return ChapterPojo.builder()
                .number(number)
                .versePojo(verses.stream().map(VerseEntity::getVerseExclChapterPojo).toList())
                .build();
    }
}
