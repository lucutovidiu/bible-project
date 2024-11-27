import {
  BibleToastrService,
  BookEditInfo,
  buildVerseFooterDetails,
  HtmlFunctions,
} from '../../';

export class ClipboardCopyService {
  constructor(
    private readonly bookEditInfo: BookEditInfo | null,
    private readonly bibleToastrService: BibleToastrService,
  ) {}

  public copyTextToClipboard() {
    const completeVerse = this.getCompleteVerse();
    completeVerse && HtmlFunctions.copyTextToClipboard(completeVerse);
  }

  private getCompleteVerse(): string | null {
    const bookEditInfo = this.bookEditInfo;
    if (!bookEditInfo) {
      return null;
    }
    const verseInfo = buildVerseFooterDetails(bookEditInfo);
    verseInfo.length > 0 && this.displayCopyMessage(verseInfo);

    return `${bookEditInfo.textWithDiacritics}\n${verseInfo}`;
  }

  private displayCopyMessage(verseInfo: string) {
    this.bibleToastrService.info(verseInfo, 'Verset copiat', false, 1000);
  }
}
