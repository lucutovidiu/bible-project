package ro.bible.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.persistence.repository.BookRepository;
import ro.bible.persistence.repository.ChapterRepository;
import ro.bible.persistence.repository.VerseRepository;
import ro.bible.api.model.BibleBookInfoDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class MenuService {

    @Inject
    BookRepository bookRepository;
    @Inject
    ChapterRepository chapterRepository;
    @Inject
    VerseRepository verseRepository;

    public List<BibleBookInfoDto> getBibleBooks() {
        return bookRepository.getAllBookPojoNoChapters().stream()
                .map(bookPojo -> new BibleBookInfoDto().setFromBookPojo(bookPojo))
                .map(bibleBookInfoDto -> bibleBookInfoDto.setChaptersCount(
                        chapterRepository.getChapterCountByBook(bibleBookInfoDto.getBookId())))
                .collect(toList());
    }
}
