package ro.bible.persistence.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.persistence.entity.ChapterEntity;
import ro.bible.persistence.model.ChapterPojo;
import ro.bible.persistence.repository.ChapterRepository;

import java.util.Optional;

@ApplicationScoped
public class ChapterService {
    @Inject
    ChapterRepository chapterRepository;

    public Optional<ChapterPojo> getChapterByBookAndChapterNumber(long bookId, int chapterNumber) {
        return chapterRepository.getChapterByBookAndChapterNumber(bookId, chapterNumber)
                .map(ChapterEntity::getChapterExclVersePojo);
    }

    public Optional<ChapterPojo> getChapterByBookAndChapterNumberInclVerses(long bookId, int chapterNumber) {
        return chapterRepository.getChapterByBookAndChapterNumber(bookId, chapterNumber)
                .map(ChapterEntity::getFullChapterPojo);
    }

    public long getChapterCountByBookId(long bookId) {
        return chapterRepository.getChapterCountByBookId(bookId);
    }
}
