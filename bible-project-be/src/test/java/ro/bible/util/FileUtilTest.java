package ro.bible.util;

import org.junit.jupiter.api.Test;
import ro.bible.shared.util.FileUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @Test
    public void test_getFileFromClasspath() {
        Optional<String> fileFromClasspath = FileUtil.getFileFromClasspath("bible-source-documents/books-metadata/book-table.json");
        assertTrue(fileFromClasspath.isPresent());
    }

}
