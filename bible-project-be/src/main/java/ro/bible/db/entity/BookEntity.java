package ro.bible.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.bible.db.pojo.BookPojo;
import ro.bible.yahwehtora.dto.BookInfo;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "exp_chapters_count")
    private Integer expChaptersCount;
    @Column(name = "exp_total_verses")
    private Integer expTotalVerses;
    @Column(name = "downloaded_link")
    private String downloadedLink;
    @Column(name = "requires_update")
    private boolean requiresUpdate;
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
                .expChaptersCount(expChaptersCount)
                .expTotalVerses(expTotalVerses)
                .downloadedLink(downloadedLink)
                .requiresUpdate(requiresUpdate)
                .build();
    }

    public void updateBook(BookInfo bookInfo) {
        this.abbreviation = bookInfo.abbreviation();
        this.testament = bookInfo.testament();
        this.expChaptersCount = bookInfo.chaptersCount();
        this.expTotalVerses = bookInfo.totalVerses();
    }
}
