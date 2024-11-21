package ro.bible.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bible.importexport.util.BookPojoYamlPathUtil;
import ro.bible.shared.model.BookInfo;
import ro.bible.shared.util.FileUtil;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookPojo {
    private long bookId;
    private String name;
    private String hebrewName;
    private String abbreviation;
    private String testament;
    private Integer expChaptersCount;
    private Integer expTotalVerses;
    private String downloadedLink;
    private Boolean requiresUpdate;
    private Boolean inProgress;
    private int bookOrder;
    private List<ChapterPojo> chapterPojo;

    public String chapterKey() {
        return name.equalsIgnoreCase("Psalmii – Tehillim") ? "Psalmul" : "Capitolul";
    }

    public BookInfo toBookInfo() {
        BookPojoYamlPathUtil bookPojoYamlPathUtil = new BookPojoYamlPathUtil(testament, abbreviation);
        bookPojoYamlPathUtil.setResourceFolder(FileUtil.CLASSPATH_BIBLE_BASE_FOLDER_NAME);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookOrder(bookOrder);
        bookInfo.setBookName(name);
        bookInfo.setBookHebrewName(hebrewName);
        bookInfo.setExpChaptersCount(expChaptersCount);
        bookInfo.setExpTotalVerses(expTotalVerses);
        bookInfo.setAbbreviation(abbreviation);
        bookInfo.setTestament(testament);
        bookInfo.setDownloadUrl(downloadedLink);
        bookInfo.setBaseFolderPath(bookPojoYamlPathUtil.getYamlFullBasePath());
        bookInfo.setStoredFileName(bookPojoYamlPathUtil.getYamlFileName());

        return bookInfo;
    }
}
