package ro.bible.db.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.entity.ChapterEntity;
import ro.bible.db.pojo.ChapterPojo;
import ro.bible.db.repo.ChapterRepository;

import java.util.Optional;

@ApplicationScoped
public class ChapterService {
    @Inject
    ChapterRepository chapterRepository;

    public Optional<ChapterPojo> getChapterByBookAndChapterNumber(long bookId, int chapterNumber) {
        return chapterRepository.getChapterByBookAndChapterNumber(bookId, chapterNumber)
                .map(ChapterEntity::getChapterPojo);
    }

    public Optional<ChapterPojo> getChapterByBookAndChapterNumberInclVerses(long bookId, int chapterNumber) {
        return chapterRepository.getChapterByBookAndChapterNumber(bookId, chapterNumber)
                .map(ChapterEntity::getChapterPojoInlVerses);
    }

    public long getChapterCountByBookId(long bookId) {
        return chapterRepository.getChapterCountByBookId(bookId);
    }
}
