import {
  BibleToastrService,
  BookEditInfo,
  buildVerseFooterDetails,
  HtmlFunctions,
} from '../../';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ClipboardCopyService {
  private bookVersesMap = new Map<string, BookEditInfo[]>();
  private clipboardCopyProcess: number = 0;

  constructor(private readonly bibleToastrService: BibleToastrService) {}

  public copyTextToClipboard(bookEditInfo: BookEditInfo | null) {
    if (bookEditInfo) {
      this.pushNewVerseToMap(bookEditInfo);
      this.copyVersesFromMapToClipboardManager();
      this.displayCopyMessage(buildVerseFooterDetails(bookEditInfo));
      clearTimeout(this.clipboardCopyProcess);
    }

    this.clipboardCopyProcess = setTimeout(() => {
      console.log('Copy to Clipboard Reset');
      this.bookVersesMap.clear();
    }, 5000);
  }

  private pushNewVerseToMap(bookEditInfo: BookEditInfo) {
    const key = this.getCreateMapKey(bookEditInfo);
    let bookEditInfos = this.bookVersesMap.get(key);
    if (bookEditInfos) {
      bookEditInfos.push(bookEditInfo);
      bookEditInfos = this.removeDuplicatesByVerseNumber(bookEditInfos);
      bookEditInfos = this.sortByVerseNo(bookEditInfos);
    } else {
      bookEditInfos = [bookEditInfo];
    }

    this.bookVersesMap.set(key, bookEditInfos);
    console.log(this.bookVersesMap);
  }

  private getCreateMapKey(bookEditInfo: BookEditInfo) {
    return bookEditInfo.bookName + ' - ' + bookEditInfo.chapterNumber;
  }

  private copyVersesFromMapToClipboardManager() {
    const allVerses = this.copyFormattedVersesFromMap(this.bookVersesMap);
    this.copyVersesToClipboard(allVerses);
  }

  private copyFormattedVersesFromMap(
    bookVersesMap: Map<string, BookEditInfo[]>,
  ) {
    let allVersesClipboard = '';
    for (const key of Array.from(bookVersesMap.keys())) {
      const bookEditInfo = bookVersesMap.get(key) as BookEditInfo[];
      allVersesClipboard +=
        this.buildVersesTextFromBookEditArray(bookEditInfo) + '\n';
    }

    return allVersesClipboard;
  }

  private sortByVerseNo(booksInfo: BookEditInfo[]): BookEditInfo[] {
    return booksInfo.sort(
      (bookInfo1, bookInfo2) => bookInfo1.verseNumber - bookInfo2.verseNumber,
    );
  }

  private removeDuplicatesByVerseNumber(
    booksInfo: BookEditInfo[],
  ): BookEditInfo[] {
    const uniqueMap = new Map<number, BookEditInfo>();

    // Iterate through the array and map by verseNumber
    booksInfo.forEach((item) => {
      uniqueMap.set(item.verseNumber, item); // Keeps the last occurrence
    });

    // Convert back to an array
    return Array.from(uniqueMap.values());
  }

  private copyVersesToClipboard(allVersesClipboard: string) {
    allVersesClipboard && HtmlFunctions.copyTextToClipboard(allVersesClipboard);
  }

  private buildVersesTextFromBookEditArray(booksInfo: BookEditInfo[]): string {
    const allVersesText = this.buildAllVersesText(booksInfo);
    const allVersesFooter = this.buildFooterForAllVerses(booksInfo);
    return `${allVersesText}\n${allVersesFooter}`;
  }

  private buildAllVersesText(booksInfo: BookEditInfo[]): string {
    return booksInfo
      .map(
        (bookInfo) => bookInfo.verseNumber + '. ' + bookInfo.textWithDiacritics,
      )
      .filter((verse) => verse !== null)
      .reduce((prev, curr) => {
        prev += '\n' + curr;
        return prev;
      });
  }

  private buildFooterForAllVerses(booksInfo: BookEditInfo[]): string {
    if (booksInfo.length > 0) {
      const bookAbrevName = booksInfo[0].abbreviation || '';
      const bookChapterNumber = booksInfo[0].chapterNumber;
      const bookVerses = booksInfo
        .map((bookInfo) => '' + bookInfo.verseNumber)
        .reduce((prev, next) => {
          prev += ',' + next;
          return prev;
        });

      return `(${bookAbrevName} ${bookChapterNumber}:${bookVerses})`;
    }

    return '';
  }

  private displayCopyMessage(verseInfo: string) {
    this.bibleToastrService.resetToasts();
    this.bibleToastrService.info(verseInfo, 'Verset copiat', false, 1500);
  }
}
