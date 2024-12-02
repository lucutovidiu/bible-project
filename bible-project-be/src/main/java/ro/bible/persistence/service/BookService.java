package ro.bible.persistence.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ro.bible.persistence.entity.BookEntity;
import ro.bible.persistence.entity.ChapterEntity;
import ro.bible.persistence.entity.VerseEntity;
import ro.bible.persistence.model.BookPojo;
import ro.bible.persistence.model.ChapterPojo;
import ro.bible.persistence.model.VersePojo;
import ro.bible.persistence.repository.BookRepository;
import ro.bible.persistence.repository.ChapterRepository;
import ro.bible.persistence.repository.VerseRepository;
import ro.bible.shared.model.BibleVerseDto;
import ro.bible.shared.model.BookInfo;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService {
    public static final String PLACE_HOLDER_MSG = "TO_BE_DONE_LATER";

    @Inject
    BookRepository bookRepository;
    @Inject
    ChapterRepository chapterRepository;
    @Inject
    VerseRepository verseRepository;

    @Transactional
    public void writeBibleVerse(BibleVerseDto bibleVerseDto) {
        BookEntity bookEntity = getOrCreateBookEntity(bibleVerseDto.getBookName());
        ChapterEntity chapterEntity = getOrCreateBookChapterEntity(bookEntity, bibleVerseDto.getChapterNumer());
        createOrUpdateVerse(chapterEntity, bibleVerseDto);
    }

    @Transactional
    public void createOrUpdateVerse(ChapterEntity chapterEntity, BibleVerseDto bibleVerseDto) {
        Optional<VerseEntity> foundVerse = chapterEntity.getVerses().stream().filter(c -> c.getVerseNumber() == bibleVerseDto.getVerseNumber()).findFirst();

        if (foundVerse.isEmpty()) {
            VerseEntity newVerse = new VerseEntity();
            newVerse.setVerseNumber(bibleVerseDto.getVerseNumber());
            newVerse.setText(bibleVerseDto.getVerseText());
            newVerse.setTextWithDiacritics(bibleVerseDto.getVerseTextWithDiacritics());
            newVerse.setChapter(chapterEntity);

            verseRepository.persist(newVerse);
        } else {
            VerseEntity verseEntity = foundVerse.get();
            verseEntity.setText(bibleVerseDto.getVerseText());
            verseEntity.setTextWithDiacritics(bibleVerseDto.getVerseTextWithDiacritics());
            verseEntity.setChapter(chapterEntity);

            verseRepository.persist(verseEntity);
        }
    }

    @Transactional
    public ChapterEntity getOrCreateBookChapterEntity(BookEntity bookEntity, int chapterNumer) {
        Optional<ChapterEntity> foundChapter = bookEntity.getChapters().stream().filter(chapterEntity -> chapterEntity.getNumber() == chapterNumer).findFirst();

        if (foundChapter.isEmpty()) {
            ChapterEntity chapterEntity = new ChapterEntity();
            chapterEntity.setNumber(chapterNumer);
            chapterEntity.setBook(bookEntity);
            bookEntity.addChapter(chapterEntity);

            chapterRepository.persist(chapterEntity);
            return chapterEntity;
        }

        return foundChapter.get();
    }

    @Transactional
    public BookEntity getOrCreateBookEntity(String bookName) {
        Optional<BookEntity> book = bookRepository.findByBookName(bookName);

        if (book.isEmpty()) {
            Log.infof("Create Book: %s", bookName);
            BookEntity bookEntity = new BookEntity();
            bookEntity.setName(bookName);
            bookEntity.setAbbreviation(PLACE_HOLDER_MSG);
            bookEntity.setTestament(PLACE_HOLDER_MSG);
            bookEntity.setExpChaptersCount(0);
            bookEntity.setExpTotalVerses(0);

            bookRepository.persist(bookEntity);
            return bookEntity;
        }

        Log.infof("Book found: %s, no need to create", bookName);
        return book.get();
    }

    @Transactional
    public Optional<BookPojo> getOrCreateBookPojo(String bookName) {
        return bookRepository.findByBookName(bookName).map(BookEntity::getBookPojoNoChapters);
    }

    @Transactional
    public List<BookPojo> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookEntity::getBookPojoNoChapters)
                .toList();
    }

    @Transactional
    public void updateOrCreateBook(BookInfo bookInfo) {
        getOrCreateBookEntity(bookInfo.getBookName()).updateBook(bookInfo);
    }

    @Transactional
    public Optional<BookPojo> getEntireBookByName(String bookName) {
        return bookRepository.findByBookName(bookName).map(BookEntity::getBookPojo);
    }

    @Transactional
    public void setBookUpdatedStatus(String bookName, boolean status) {
        bookRepository.findByBookName(bookName)
                .ifPresent(bookEntity -> {
                    bookEntity.setRequiresUpdate(status);
                    bookEntity.setInProgress(status);
                });
    }

    @Transactional
    public boolean isBoolAlreadyTakenIfNotMarketItAsTaken(long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId);
        if(!bookEntity.isInProgress()) {
            bookEntity.setInProgress(true);
            return false;
        }

        return true;
    }

    @Transactional
    public void bulkPatchVersesToDb(BookPojo bookPojo) {
        Optional<BookEntity> byBookName = bookRepository.findByBookName(bookPojo.getName());

        if(byBookName.isPresent()) {
            Log.infof("Pathing, bookName: '%s'", bookPojo.getName());
            BookEntity bookEntity = byBookName.get();
            bookEntity.getChapters()
                    .forEach(chapterEntity -> updateChapter(chapterEntity,bookPojo));
        } else {
            Log.errorf("Book NOT Found: '%s', for pathing", bookPojo.getName());
        }
    }

    private void updateChapter(ChapterEntity chapterEntity, BookPojo bookPojo) {
        Optional<ChapterPojo> foundChapter = bookPojo.getChapterPojo().stream()
                .filter(chapterPojo -> chapterPojo.getNumber() == chapterEntity.getNumber())
                .findFirst();

        if(foundChapter.isPresent()) {
            ChapterPojo chapterPojo = foundChapter.get();
            chapterPojo.getVersePojo()
                    .forEach(versePojo -> {
                        chapterEntity.getVerses().stream()
                                .filter(verseEntity -> verseEntity.getVerseNumber() == versePojo.getVerseNumber())
                                .findFirst()
                                .ifPresent(verseEntity -> updateVerse(verseEntity, versePojo));
                    });
        } else {
            Log.errorf("Chapter NOT found no: '%s', for updating", chapterEntity.getNumber());
        }
    }

    private void updateVerse(VerseEntity verseEntity, VersePojo versePojo) {
        verseEntity.setText(versePojo.getText());
        verseEntity.setTextWithDiacritics(versePojo.getTextWithDiacritics());
    }

    public long getBookCount() {
        return bookRepository.count();
    }
}
