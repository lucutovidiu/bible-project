package ro.bible.db.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import ro.bible.db.entity.ChapterEntity;

import java.util.Optional;

@ApplicationScoped
public class ChapterRepository implements PanacheRepository<ChapterEntity> {
    public long getChapterCountByBook(long bookId) {
        return count("SELECT COUNT(c) FROM ChapterEntity c WHERE c.book.id = ?1", bookId);
    }

    public Optional<ChapterEntity> getChapterByBookAndChapterNumber(long bookId, Integer chapterNumer) {
        return find("SELECT c FROM ChapterEntity c WHERE c.number = ?1 and c.book.id = ?2",
                chapterNumer, bookId)
                .firstResultOptional();
    }

    public long getChapterCountByBookId(long bookId) {
        return count("SELECT count(c) FROM ChapterEntity c WHERE c.book.id = ?1", bookId);
    }
}
