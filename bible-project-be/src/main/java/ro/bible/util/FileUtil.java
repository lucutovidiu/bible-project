package ro.bible.util;

import io.quarkus.logging.Log;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class FileUtil {

    public static final String RESOURCE_FOLDER = "src/main/resources";

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
/*

    // under resources folder just get path inside like: bible-source-documents/menu.html
    public Optional<URI> resolveResourcePath(String path) {
        Log.infof("Resolving resource file path: '%s'", path);
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        if (resource != null) {
            Log.infof("Resolved resource file path: '%s'", path);
            try {
                return Optional.of(resource.toURI().);
            } catch (URISyntaxException e) {
                Log.errorf("Can't resolve resource file path: '%s'", path);
            }
        }

        Log.errorf("Can't resolve resource file path: '%s'", path);
        return Optional.empty();
    }
    public static Optional<String> getFileContentAsString(String path) {
        try {
            Optional<Path> pathFound = resolveResourcePath(path).map(FileUtil::toPath);
            if (pathFound.isPresent()) {
                return Optional.of(new String(Files.readAllBytes(pathFound.get())));
            }
        } catch (IOException e) {
            Log.errorf("Could not read file '%s'", path);
        }
        return Optional.empty();
    }

    public boolean createFolderIfNotExists(String folderPath) {
        Optional<URI> path = resolveResourcePath(folderPath);

        if (path.isPresent()) {
            if (!doesFolderExists(folderPath)) {
                try {
                    Log.infof("creating folder: '%s'", folderPath);
                    Files.createDirectory(Path.of(path.get()));
                    return true;
                } catch (IOException e) {
                    Log.errorf("Can't create directory: '%s'", folderPath);
                }
            }
        }


        return false;
    }

    public Path toPath(URI uri) {
        return Paths.get(uri);
    }

    public boolean doesFolderExists(String folderPath) {
        Optional<URI> path = resolveResourcePath(folderPath);
        return path.filter(uri -> Files.exists(toPath(uri)) && Files.isDirectory(toPath(uri))).isPresent();
    }

    public boolean createFileIfNotExists(String basePath, String fileName) {
        Optional<URI> path = resolveResourcePath(basePath);

        if (path.isPresent()) {
            try {
                System.out.println("creating file: " + fileName);
                Files.createFile(Path.of(path.get().getPath() + File.pathSeparator + fileName));
                return true;
            } catch (IOException e) {
                System.out.println("Can't create file: " + fileName);
            }
        }
        return false;
    }

    public boolean doesFileExists(String filePath) {
        Optional<URI> path = resolveResourcePath(filePath);
        return path.filter(uri -> Files.exists(toPath(uri)) && Files.isRegularFile(toPath(uri))).isPresent();
    }

    public boolean writeContentToFile(String filePath, String content) {
        // Define the path to the resources folder (works in dev mode)
        Optional<URI> path = resolveResourcePath(filePath);

        if (path.isPresent()) {
            // Write the content to the file
            try {
                Files.writeString(toPath(path.get()), content);
                Log.infof("File writen to location: '%s'", filePath);
                return true;
            } catch (IOException e) {
                Log.errorf("Fail to Write file to location: '%s'", filePath);
            }
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
    }*/
}
