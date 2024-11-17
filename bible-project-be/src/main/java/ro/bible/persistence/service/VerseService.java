package ro.bible.persistence.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ro.bible.persistence.repository.VerseRepository;

@ApplicationScoped
public class VerseService {
    @Inject
    VerseRepository verseRepository;

    public long getVerseCountByBookId(long bookId) {
        return verseRepository.getVerseCountByBookId(bookId);
    }

    @Transactional
    public void updateSearchVector(long bookId) {
        int updatesRecordsCount = verseRepository.updateSearchVector(bookId);
        Log.infof("Search Vector updates records count: %d", updatesRecordsCount);
    }
}
