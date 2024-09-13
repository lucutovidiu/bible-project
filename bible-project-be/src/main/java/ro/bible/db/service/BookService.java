package ro.bible.db.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ro.bible.db.entity.BookEntity;
import ro.bible.db.entity.ChapterEntity;
import ro.bible.db.entity.VerseEntity;
import ro.bible.db.pojo.BookPojo;
import ro.bible.db.repo.BookRepository;
import ro.bible.db.repo.ChapterRepository;
import ro.bible.db.repo.VerseRepository;
import ro.bible.yahwehtora.dto.BibleVerseDto;
import ro.bible.yahwehtora.dto.BookInfo;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService {
    private static final String PLACE_HOLDER_MSG = "TO_BE_DONE_LATER";

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

            return chapterEntity;
        }

        return foundChapter.get();
    }

    @Transactional
    public BookEntity getOrCreateBookEntity(String bookName) {
        Optional<BookEntity> book = bookRepository.findByBookName(bookName);

        if (book.isEmpty()) {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setName(bookName);
            bookEntity.setAbbreviation(PLACE_HOLDER_MSG);
            bookEntity.setTestament(PLACE_HOLDER_MSG);

            return bookEntity;
        }

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
    public void updateBook(BookInfo bookInfo) {
        getOrCreateBookEntity(bookInfo.bookName()).updateBook(bookInfo);
    }

    @Transactional
    public void updateBookUrl(String bookName, String downloadLink) {
        bookRepository.findByBookName(bookName)
                .ifPresent(bookEntity -> bookEntity.setDownloadedLink(downloadLink));
    }
}
