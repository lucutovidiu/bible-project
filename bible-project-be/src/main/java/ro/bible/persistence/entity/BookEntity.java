package ro.bible.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.bible.persistence.model.BookPojo;
import ro.bible.shared.model.BookInfo;

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
    @Column(name = "in_progress")
    private boolean inProgress;
    @Column(name = "book_order")
    private int bookOrder;
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
                .inProgress(inProgress)
                .bookOrder(bookOrder)
                .build();
    }

    public void updateBook(BookInfo bookInfo) {
        this.abbreviation = bookInfo.getAbbreviation();
        this.testament = bookInfo.getTestament();
        this.expChaptersCount = bookInfo.getExpChaptersCount();
        this.expTotalVerses = bookInfo.getExpTotalVerses();
        this.downloadedLink = bookInfo.getDownloadUrl();
        this.bookOrder = bookInfo.getBookOrder();
    }
}