package ro.bible.db.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import ro.bible.db.entity.VerseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class VerseRepository implements PanacheRepository<VerseEntity> {

    public List<VerseEntity> findPlacesInTheBibleByVerseTextFullTextSearch(String verseText) {
        // | OR, & AND
        String sql = "SELECT * FROM verses WHERE search_vector @@ plainto_tsquery(:searchText)";
        return getEntityManager().createNativeQuery(sql, VerseEntity.class)
                .setParameter("searchText", String.join(" | ", verseText.split(" ")))
                .getResultList();
    }

    public List<VerseEntity> findPlacesInTheBibleByVerseText(String verseText) {
        return list("text like ?1", "%" + verseText + "%");
    }

    public long getVerseCountByBookId(long bookId) {
        return count("SELECT count(v) FROM VerseEntity v WHERE v.chapter.book.id = ?1", bookId);
    }
}
