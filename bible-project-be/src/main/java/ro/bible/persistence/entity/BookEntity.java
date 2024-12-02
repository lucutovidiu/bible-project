package ro.bible.persistence.entity;

import io.quarkus.logging.Log;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.bible.persistence.model.BookPojo;
import ro.bible.persistence.model.ChapterPojo;
import ro.bible.shared.model.BookInfo;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    @Column(name = "name", length = 70)
    private String name;
    @Column(name = "hebrew_name", length = 70)
    private String hebrewName;
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
                .hebrewName(hebrewName)
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

    public BookPojo getBookPojo() {
        return BookPojo.builder()
                .bookId(id)
                .name(name)
                .hebrewName(hebrewName)
                .abbreviation(abbreviation)
                .testament(testament)
                .expChaptersCount(expChaptersCount)
                .expTotalVerses(expTotalVerses)
                .downloadedLink(downloadedLink)
                .requiresUpdate(requiresUpdate)
                .inProgress(inProgress)
                .bookOrder(bookOrder)
                .chapterPojo(chapters.stream().map(ChapterEntity::getChapterExclBookPojo).collect(toList()))
                .build();
    }

    public void updateBook(BookInfo bookInfo) {
        Log.infof("Update book: %s", bookInfo.getBookName());
        this.abbreviation = bookInfo.getAbbreviation();
        this.testament = bookInfo.getTestament();
        this.expChaptersCount = bookInfo.getExpChaptersCount();
        this.expTotalVerses = bookInfo.getExpTotalVerses();
        this.downloadedLink = bookInfo.getDownloadUrl();
        this.bookOrder = bookInfo.getBookOrder();
        this.hebrewName = bookInfo.getBookHebrewName();
    }
}
