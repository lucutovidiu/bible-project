package ro.bible.resourses.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.entity.ChapterEntity;
import ro.bible.db.entity.VerseEntity;
import ro.bible.db.pojo.VersePojo;
import ro.bible.db.repo.ChapterRepository;
import ro.bible.db.repo.VerseRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class BibleFinderService {

    @Inject
    ChapterRepository chapterRepository;
    @Inject
    VerseRepository verseRepository;

    public List<VersePojo> getChapterVerses(Integer chapterNumer, long bookId) {
        ChapterEntity chapter = chapterRepository.getChapterByBookAndChapterNumber(bookId, chapterNumer).orElseThrow(() -> new RuntimeException("Chapter not found"));

        return chapter.getVerses().stream()
                .map(VerseEntity::getVersePojo)
                .collect(toList());
    }

    public List<VersePojo> findPlacesInTheBibleByVerseText(String verseText) {

        List<VersePojo> versePojoList = verseRepository.findPlacesInTheBibleByVerseTextFullTextSearch(verseText).stream()
                .map(VerseEntity::getVerseInclChapterPojo)
                .collect(toList());

        if(versePojoList.isEmpty()) {
            return verseRepository.findPlacesInTheBibleByVerseText(verseText).stream()
                    .map(VerseEntity::getVerseInclChapterPojo)
                    .collect(toList());
        }
        return versePojoList;
    }
}
