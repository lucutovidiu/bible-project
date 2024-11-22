package ro.bible.persistence.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.bible.api.model.request.BibleVerseEditRequestDto;
import ro.bible.htmlparser.util.BibleStringUtils;
import ro.bible.persistence.entity.VerseEntity;
import ro.bible.persistence.repository.VerseRepository;

import java.util.List;

@ApplicationScoped
public class VerseService {
    private static final Logger log = LoggerFactory.getLogger(VerseService.class);
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

    @Transactional
    public boolean patchBibleVerse(BibleVerseEditRequestDto requestDto) {
        Log.infof("Retrieving book by id: %d, book name: %s", requestDto.getBookId(), requestDto.getBookName());
        return verseRepository.findVerseByBookIdAndChapterNumber(requestDto.getBookId(), requestDto.getChapterNumber(), requestDto.getVerseNumber())
                .map(verseEntity -> {
                    verseEntity.setTextWithDiacritics(requestDto.getTextWithDiacritics());
                    verseEntity.setText(BibleStringUtils
                            .removeSpecialChars(BibleStringUtils
                                    .removeDiacritics(requestDto.getTextWithDiacritics())));
                    Log.infof("Patched verse id: %s", verseEntity.id);
                    return true;
                }).orElse(false);
    }
}
