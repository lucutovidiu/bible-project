package ro.bible.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.persistence.entity.ChapterEntity;
import ro.bible.persistence.entity.VerseEntity;
import ro.bible.persistence.model.VersePojo;
import ro.bible.persistence.repository.ChapterRepository;
import ro.bible.persistence.repository.VerseRepository;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class BibleFinderService {

    @Inject
    ChapterRepository chapterRepository;
    @Inject
    VerseRepository verseRepository;

    private static int getTestamentPriority(String testament) {
        return switch (testament) {
            case "Noul Testament" -> 1; // Highest priority
            case "Vechiul Testament" -> 2; // Medium priority
            case "Apocrifa" -> 3; // Lowest priority
            default -> Integer.MAX_VALUE; // Unknown testaments go last
        };
    }

    public List<VersePojo> getChapterVerses(Integer chapterNumer, long bookId) {
        ChapterEntity chapter = chapterRepository.getChapterByBookAndChapterNumber(bookId, chapterNumer).orElseThrow(() -> new RuntimeException("Chapter not found"));

        return chapter.getVerses().stream()
                .map(VerseEntity::getVerseExclChapterPojo)
                .collect(toList());
    }

    public List<VersePojo> findPlacesInTheBibleByVerseText(String verseText) {

        List<VersePojo> versePojoList = verseRepository.findPlacesInTheBibleByVerseTextFullTextSearch(verseText).stream()
                .map(VerseEntity::getFullVersePojo)
                .collect(toList());

        if (versePojoList.isEmpty()) {
            return organizeVerses(verseRepository.findPlacesInTheBibleByVerseText(verseText).stream()
                    .map(VerseEntity::getFullVersePojo)
                    .collect(toList()));
        }
        return organizeVerses(versePojoList);
    }

    private List<VersePojo> organizeVerses(List<VersePojo> versePojoList) {

        versePojoList.sort(
                Comparator.comparingInt(versePojo ->
                        getTestamentPriority(versePojo.getChapter().getBook().getTestament())));

        return versePojoList;
    }
}
