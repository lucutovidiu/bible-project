package ro.bible.util;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import ro.bible.yahwehtora.dto.BookInfo;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BibleUtil {
    //https://www.biblememorygoal.com/how-many-chapters-verses-in-the-bible/

    private final List<BookInfo> bookInfoList;
    static {
        String fileContentAsString = FileUtil.getFileContentAsString("src/main/resources/bible-source-documents/menu/book-table.json");
        bookInfoList = ConvertUtil.convertFromString(fileContentAsString, new TypeReference<List<BookInfo>>(){});
    }

    public static List<BookInfo> getBookInfoList() {
        return bookInfoList;
    }

    public BookInfo getBookInfoByBookName(String bookName) throws Exception {
        return bookInfoList.stream().filter(book -> book.getBookName().startsWith(bookName.trim()))
                .findFirst().orElseThrow(() -> new Exception("BookNotFound"));
    }
}
