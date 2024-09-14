package ro.bible.maintanance.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.entity.ChapterEntity;
import ro.bible.db.pojo.BookPojo;
import ro.bible.db.repo.ChapterRepository;
import ro.bible.db.service.BookService;
import ro.bible.util.BibleStringUtils;
import ro.bible.util.BibleUtil;
import ro.bible.yahwehtora.dto.BibleVerseDto;
import ro.bible.yahwehtora.service.ChapterExtractor;
import ro.bible.yahwehtora.service.MenuExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class BookMaintenanceService {
    @Inject
    ChapterRepository chapterRepository;
    @Inject
    BookService bookService;

    // if you want to re-update with all static bookInfoList
    public void updateAllBooksFromStatic() {
        BibleUtil.bookInfoList
                .forEach(bookInfo -> {
                    try {
                        bookService.updateOrCreateBook(bookInfo);
                        Log.infof("Book %s updated", bookInfo.bookName());
                    } catch (Exception e) {
                        Log.errorf("Error getting bookInfo info for %s", bookInfo.bookName());
                    }
                });
    }

    public void buildMissingChapters() {
        // retrieve all available books as pojo
        List<BookPojo> bookPojos = bookService.getAllBooks();

        // go through all books
        bookPojos.forEach(bookPojo -> {
            // verify chapter count ok
            boolean isChaptersCountOk = verifyChaptersCount(bookPojo);
            if (!isChaptersCountOk) {
                // build missing chapter list
                List<Integer> missingChapters = buildMissingChapters(bookPojo);
                missingChapters.forEach(chapter -> {
                    Log.infof("Patching chapter %d", chapter);
//                    patchMissingChapter(bookPojo, chapter);
                });
            }
        });
    }

    private List<Integer> buildMissingChapters(BookPojo bookPojo) {
        List<Integer> missingChapter = new ArrayList<>();
        for (int i = 1; i <= bookPojo.getExpChaptersCount(); i++) {
            Optional<ChapterEntity> chapterEntity = chapterRepository.getChapterByBookAndChapterNumber(i, bookPojo.getBookId());
            if (chapterEntity.isEmpty()) {
                missingChapter.add(i);
            }
        }

        return missingChapter;
    }

    private boolean verifyChaptersCount(BookPojo bookPojo) {
        long actualCount = chapterRepository.getChapterCountByBookId(bookPojo.getBookId());
        return actualCount == bookPojo.getExpChaptersCount();
    }

    public void patchAllBooks() {
        List.of("");
        bookService.getAllBooks()
                .stream()
//                .filter(book -> book.getTestament().equals("Noul Testament"))
                .forEach(book -> {
                    System.out.println(book.getName());
//            pathWholeBookByName(book.getName());
        });
    }







}
