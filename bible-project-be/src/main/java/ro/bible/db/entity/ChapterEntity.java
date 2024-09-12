package ro.bible.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.bible.db.pojo.ChapterPojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public ChapterPojo getChapterPojoInclBook() {
        return ChapterPojo.builder()
                .number(number)
                .book(book.getBookPojoNoChapters())
                .build();
    }
}
