package ro.bible.localfiles.util;

import com.fasterxml.jackson.core.type.TypeReference;
import io.quarkus.logging.Log;
import lombok.experimental.UtilityClass;
import ro.bible.shared.util.ConvertUtil;
import ro.bible.shared.util.FileUtil;
import ro.bible.shared.model.BookInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class BibleUtil {
    //https://www.biblememorygoal.com/how-many-chapters-verses-in-the-bible/
    private List<BookInfo> bookInfoList = new ArrayList<>();

    public static List<BookInfo> getBookInfoList() {
        if (bookInfoList.isEmpty()) {
            bookInfoList = tryGetBookListFromLocalSystem();
        }

        return bookInfoList;
    }

    private List<BookInfo> tryGetBookListFromLocalSystem() {
        Optional<List<BookInfo>> fileContentAsString = FileUtil.getFileFromClasspath(FileUtil.CLASSPATH_BOOK_TABLE_FILE_PATH)
                .map(stringFile -> ConvertUtil.convertFromString(stringFile, new TypeReference<List<BookInfo>>() {
                }));
        if (fileContentAsString.isEmpty()) {
            Log.errorf("No file found in '%s'", FileUtil.CLASSPATH_BOOK_TABLE_FILE_PATH);
            Log.errorf("Returning empty array!");
            return new ArrayList<>();
        }

        Log.infof("Successfully read BookInfo from '%s'", FileUtil.CLASSPATH_BOOK_TABLE_FILE_PATH);
        return fileContentAsString.get();
    }

    public BookInfo getBookInfoByBookName(String bookName) throws Exception {
        return bookInfoList.stream().filter(book -> book.getBookName().startsWith(bookName.trim()))
                .findFirst().orElseThrow(() -> new Exception("BookNotFound"));
    }
}
