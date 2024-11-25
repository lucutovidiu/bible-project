import { BibleBook } from '../bible-book';
import { SelectedBibleBook } from '../../store';
import { BibleVerse } from '../bible-verse';
import { BookEditInfo } from '../book-edit-info';

export function buildBookEditInfo(
  bibleBooks: BibleBook[] | null,
  selectedBook: SelectedBibleBook | null,
  bibleVerse: BibleVerse | null,
) {
  const bookEditInfo: BookEditInfo = {
    abbreviation: null,
  } as BookEditInfo;

  if (!bibleVerse) {
    return null;
  }

  bookEditInfo.textWithDiacritics = bibleVerse.textWithDiacritics;

  if (bibleVerse.chapter) {
    bookEditInfo.abbreviation = bibleVerse.chapter.book.abbreviation;
    bookEditInfo.chapterNumber = bibleVerse.chapter.number;
    bookEditInfo.verseNumber = bibleVerse.verseNumber;
    bookEditInfo.bookName = bibleVerse.chapter.book.name;
    bookEditInfo.bookId = bibleVerse.chapter.book.bookId;
    return bookEditInfo;
  }

  if (!selectedBook) {
    return null;
  }

  if (!bibleBooks) {
    bookEditInfo.chapterNumber = selectedBook.chapterNumber || 0;
    bookEditInfo.verseNumber = selectedBook.verseNumber || 0;
    bookEditInfo.bookName = selectedBook.bookName || '';
    bookEditInfo.bookId = selectedBook.bookId || 0;
    return bookEditInfo;
  }

  const bibleBook = bibleBooks.find(
    (book) => book.bookId === selectedBook.bookId,
  );
  if (!bibleBook) return null;

  bookEditInfo.abbreviation = bibleBook.abbreviation;
  bookEditInfo.chapterNumber = selectedBook.chapterNumber || 0;
  bookEditInfo.verseNumber = bibleVerse.verseNumber;
  bookEditInfo.bookName = bibleBook.name;
  bookEditInfo.bookId = bibleBook.bookId;
  return bookEditInfo;
}
