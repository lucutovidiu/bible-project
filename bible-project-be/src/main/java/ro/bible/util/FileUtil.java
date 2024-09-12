package ro.bible.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@UtilityClass
public class FileUtil {

    public static String getFileContentAsString(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<String> getFileFromResourceAsString(String path) {
        try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            assert resourceAsStream != null;
            if(resourceAsStream.available() > 0 ) {
                return Optional.of(resourceAsStream.toString());
            }
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
