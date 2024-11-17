package ro.bible.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import ro.bible.persistence.entity.BookEntity;
import ro.bible.persistence.model.BookPojo;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class BookRepository implements PanacheRepository<BookEntity> {

    public Optional<BookEntity> findByBookName(String bookName) {
        return find("name", bookName).firstResultOptional();
    }

    public long findBookCount(String bookName) {
        return count();
    }

    public List<BookPojo> getAllBookPojoNoChapters() {
        return findAll().list().stream()
                .map(BookEntity::getBookPojoNoChapters)
                .collect(toList());
    }
}
