package ro.bible.util;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class FileUtil {

    public static final String RESOURCE_FOLDER = "src/main/resources";

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

    // under resources folder just get path inside like: bible-source-documents/htmlPage.html
    public Optional<String> getFileFromClasspath(String path) {
        try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("bible-source-documents/htmlPage.html")) {
            assert resourceAsStream != null;
            if(resourceAsStream.available() > 0 ) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))) {
                    String collect = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                    return Optional.of(collect);
                } catch (IOException e) {
                    System.out.println("Can't retrieve document path: " + path);
                }
            }
        } catch (IOException e) {
            System.out.println("Can't retrieve document path: " + path);
        }
        return Optional.empty();
    }

    public boolean createFolderIfNotExists(String folderPath) {
        Path path = Paths.get(folderPath);

        if (!doesFolderExists(folderPath)) {
            try {
                System.out.println("creating folder: " + folderPath);
                Files.createDirectory(path);
                return true;
            } catch (IOException e) {
                System.out.println("Can't create directory: " + folderPath);
            }
        }

        return false;
    }

    public boolean doesFolderExists(String folderPath) {
        Path path = Paths.get(folderPath);

        return Files.exists(path) && Files.isDirectory(path);
    }

    public boolean createFileIfNotExists(String filePath) {
        Path path = Paths.get(filePath);

        if (!doesFileExists(filePath)) {
            try {
                System.out.println("creating file: " + filePath);
                Files.createFile(path);
                return true;
            } catch (IOException e) {
                System.out.println("Can't create file: " + filePath);
            }
        }
        return false;
    }

    public boolean doesFileExists(String filePath) {
        Path path = Paths.get(filePath);

        return Files.exists(path) && Files.isRegularFile(path);
    }
}
