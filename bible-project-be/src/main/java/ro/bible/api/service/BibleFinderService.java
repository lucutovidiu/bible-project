package ro.bible.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.persistence.entity.ChapterEntity;
import ro.bible.persistence.entity.VerseEntity;
import ro.bible.persistence.model.VersePojo;
import ro.bible.persistence.repository.ChapterRepository;
import ro.bible.persistence.repository.VerseRepository;

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
