export interface BibleBook {
  bookId: number,
  name: string,
  abbreviation: string,
  testament: string,
  chaptersCount: number,
}

export interface SelectedBibleBook {
  bookName: string | null,
  bookId: number | null,
  chapterNumber: number | null,
  verseNumber: number | null,
}

export function createSelectedBook(): SelectedBibleBook {
  return {
    bookName: null,
    bookId: null,
    chapterNumber: null,
    verseNumber: null,
  }
}
