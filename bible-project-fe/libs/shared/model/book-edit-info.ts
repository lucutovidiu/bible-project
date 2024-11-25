export interface BookEditInfo {
  abbreviation: string | null;
  textWithDiacritics: string;
  bookName: string;
  bookId: number;
  chapterNumber: number;
  verseNumber: number;
}

export function buildVerseFooterDetails(bookEditInfo: BookEditInfo): string {
  if (!bookEditInfo.abbreviation)
    return `(${bookEditInfo.bookName} ${bookEditInfo.chapterNumber}:${bookEditInfo.verseNumber})`;

  return `(${bookEditInfo.abbreviation} ${bookEditInfo.chapterNumber}:${bookEditInfo.verseNumber})`;
}
