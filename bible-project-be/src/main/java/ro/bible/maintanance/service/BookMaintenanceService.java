package ro.bible.maintanance.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.db.entity.ChapterEntity;
import ro.bible.db.pojo.BookPojo;
import ro.bible.db.pojo.ChapterPojo;
import ro.bible.db.service.BookService;
import ro.bible.db.service.ChapterService;
import ro.bible.util.BibleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookMaintenanceService {
    @Inject
    ChapterService chapterService;
    @Inject
    BookService bookService;

    // if you want to re-update with all static bookInfoList
    public void updateAllBooksFromStatic() {
        BibleUtil.getBookInfoList()
                .forEach(bookInfo -> {
                    try {
                        bookService.updateOrCreateBook(bookInfo);
                        Log.infof("Book %s updated", bookInfo.getBookName());
                    } catch (Exception e) {
                        Log.errorf("Error getting bookInfo info for %s", bookInfo.getBookName());
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
            Optional<ChapterPojo> chapterPojo = chapterService.getChapterByBookAndChapterNumber(bookPojo.getBookId(), i);
            if (chapterPojo.isEmpty()) {
                missingChapter.add(i);
            }
        }

        return missingChapter;
    }

    private boolean verifyChaptersCount(BookPojo bookPojo) {
        long actualCount = chapterService.getChapterCountByBookId(bookPojo.getBookId());
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
