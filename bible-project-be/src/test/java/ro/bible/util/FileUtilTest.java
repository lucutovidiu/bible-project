package ro.bible.util;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @Test
    public void test_getFileFromClasspath() {
        Optional<String> fileFromClasspath = FileUtil.getFileFromClasspath("bible-source-documents/menu/book-table.json");
        assertTrue(fileFromClasspath.isPresent());
    }

}