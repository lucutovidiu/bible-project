package ro.bible.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.bible.db.pojo.BookPojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    @Column(name = "name", length = 70)
    private String name;
    @Column(name = "abbreviation", length = 20)
    private String abbreviation;
    @Column(name = "testament", length = 30)
    private String testament;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "book")
    private List<ChapterEntity> chapters = new ArrayList<>();

    public void addChapter(ChapterEntity chapterEntity) {
        chapters.add(chapterEntity);
    }

    public BookPojo getBookPojoNoChapters() {
        return BookPojo.builder()
                .bookId(id)
                .name(name)
                .abbreviation(abbreviation)
                .testament(testament)
                .build();
    }
}
