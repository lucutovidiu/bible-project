package ro.bible.shared.util;

import io.quarkus.logging.Log;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class FileUtil {

    public static final String RESOURCE_FOLDER = "src/main/resources/bible-source-documents";
    public static final String BIBLE_RESOURCE_FOLDER = "bible-source-documents";

    public static String getFileContentAsString(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // under resources folder just get path inside like: bible-source-documents/menu.html
    public Optional<String> getFileFromClasspath(String path) {
        try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            if (resourceAsStream == null) {
                return Optional.empty();
            }
            if (resourceAsStream.available() > 0) {
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
                Log.infof("creating folder: '%s'", folderPath);
                Files.createDirectory(path);
                return true;
            } catch (IOException e) {
                Log.errorf("Can't create directory: '%s'", folderPath);
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

    public boolean writeContentToFile(String filePath, String content) {
        // Define the path to the resources folder (works in dev mode)
        Path path = Paths.get(filePath);

        // Write the content to the file
        try {
            Files.writeString(path, content);
            Log.infof("File writen to location: '%s'", filePath);
            return true;
        } catch (IOException e) {
            Log.errorf("Fail to Write file to location: '%s'", filePath);
        }

        return false;
    }

    public BasicFileAttributes readFileAttributes(Path filePath) {
        try {
            return Files.readAttributes(filePath, BasicFileAttributes.class);
        } catch (IOException e) {
            Log.errorf("Error reading file attributes: %s", e.getMessage());
        }
        return null;
    }

    public List<Path> getMostRecentFiles(List<Path> input, int limit) {
        return input.stream()
                .filter(Files::isRegularFile)  // Filter only regular files
                .sorted(Comparator.comparing(path -> {
                    BasicFileAttributes basicFileAttributes = readFileAttributes((Path) path);
                    if (basicFileAttributes == null) {
                        return FileTime.from(Instant.now());
                    }
                    return basicFileAttributes.creationTime();
                }).reversed())
                .limit(limit)
                .toList();
    }

    public void dropFilesApartFromMostRecent(String basePath, String fileStartingWith, int limit) {
        try (Stream<Path> paths = Files.list(Paths.get(basePath))) {
            Log.infof("Filtering files by name starting with: '%s'", fileStartingWith);
            List<Path> filterOnlyRequired = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(fileStartingWith))
                    .toList();

            Log.infof("Getting more recent top: '%s' files", limit);
            List<Path> mostRecentFiles = getMostRecentFiles(filterOnlyRequired, limit);

            Log.info("Removing older files");
            filterOnlyRequired.stream()
                    .filter(file -> !mostRecentFiles.contains(file))
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                            Log.infof("File: '%s' deleted", file.getFileName().toAbsolutePath().getFileName().toString());
                        } catch (IOException e) {
                            Log.errorf("File name: '%s', can not be deleted", file.getFileName().toString(), e);
                        }
                    });
        } catch (IOException e) {
            Log.errorf("Can not read path: %s", basePath, e);
        }
    }
}
