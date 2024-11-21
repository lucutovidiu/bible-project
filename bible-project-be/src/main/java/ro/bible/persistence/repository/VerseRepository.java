package ro.bible.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import ro.bible.persistence.entity.VerseEntity;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VerseRepository implements PanacheRepository<VerseEntity> {

    public List<VerseEntity> findPlacesInTheBibleByVerseTextFullTextSearch(String verseText) {
        // | OR, & AND
        String sql = "SELECT * FROM verses WHERE search_vector @@ plainto_tsquery(:searchText)";
        return getEntityManager().createNativeQuery(sql, VerseEntity.class)
                .setParameter("searchText", String.join(" | ", verseText.split(" ")))
                .getResultList();
    }

    public int updateSearchVector(long bookId) {
        String spql = "UPDATE verses v SET search_vector = to_tsvector('english', text || ' ') where v.chapter_id in (select id from chapters c where c.book_id=:bookId)";
        return getEntityManager().createNativeQuery(spql)
                .setParameter("bookId", bookId)
                .executeUpdate();
    }

    public List<VerseEntity> findPlacesInTheBibleByVerseText(String verseText) {
        return list("text like ?1", "%" + verseText + "%");
    }

    public long getVerseCountByBookId(long bookId) {
        return count("SELECT count(v) FROM VerseEntity v WHERE v.chapter.book.id = ?1", bookId);
    }

    public Optional<VerseEntity> findVerseByBookIdAndChapterNumber(long bookId, int chapterNumber) {
        return find("SELECT v FROM VerseEntity v WHERE v.chapter.book.id = ?1 and v.chapter.number = ?2", bookId, chapterNumber)
                .firstResultOptional();
    }
}
