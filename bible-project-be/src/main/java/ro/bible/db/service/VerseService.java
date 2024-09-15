package ro.bible.db.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.entity.ChapterEntity;
import ro.bible.db.pojo.ChapterPojo;
import ro.bible.db.repo.VerseRepository;

import java.util.Optional;

@ApplicationScoped
public class VerseService {
    @Inject
    VerseRepository verseRepository;

    public long getVerseCountByBookId(long bookId) {
        return verseRepository.getVerseCountByBookId(bookId);
    }
}
