package ro.bible.filewriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BibleFileWriter {
    private static final String basePathPath = "/Users/ovidiulucut/Desktop/RestornedNamesRomanianBible";
    private static final String fileSeparator = File.separator;
    private static final String versesFileName = "verses.txt";
    private static final String htmlPageName = "htmlPage.html";
    private final String bookTitle;

    public BibleFileWriter(String bookTitle) {
        this.bookTitle = bookTitle;
        createBibleBaseFolderIfNotExists();
    }

    private PrintWriter getWriter(String filePath) {
        try {
            File file = Paths.get(filePath).toFile();
            return new PrintWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeHtmlPageItself(String html) {
        String htmlFilePath = basePathPath + fileSeparator + bookTitle + fileSeparator + htmlPageName;
        createFileIfNotExists(htmlFilePath);

        try (PrintWriter writer = getWriter(htmlFilePath)) {
            writer.println(html);
        }
    }

    public void writeVerses(String chapterTitle, List<String> verses) {
        String filePath = basePathPath + fileSeparator + bookTitle + fileSeparator + chapterTitle + fileSeparator + versesFileName;
        createBibleChapterFolderIfNotExists(chapterTitle);
        createFileIfNotExists(filePath);
        System.out.println("writing file: " + filePath);

        try (PrintWriter writer = getWriter(filePath)) {
            writer.println(chapterTitle);
            writer.println();
            writer.println();
            verses.forEach(writer::println);
        }
    }

    private void createBibleChapterFolderIfNotExists(String chapterTitle) {
        String bookFolderPath = basePathPath + fileSeparator + bookTitle + fileSeparator + chapterTitle;
        createFolderIfNotExists(bookFolderPath);
    }

    private void createBibleBaseFolderIfNotExists() {
        String bookFolderPath = basePathPath + fileSeparator + bookTitle;
        createFolderIfNotExists(bookFolderPath);
    }

    private void createFolderIfNotExists(String folderPath) {
        Path path = Paths.get(folderPath);

        if (!(Files.exists(path) && Files.isDirectory(path))) {
            try {
                System.out.println("creating folder: " + folderPath);
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Can't create directory: " + folderPath);
            }
        }
    }

    private void createFileIfNotExists(String filePath) {
        Path path = Paths.get(filePath);

        if (!(Files.exists(path) && Files.isRegularFile(path))) {
            try {
                System.out.println("creating file: " + filePath);
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("Can't create file: " + filePath);
            }
        }
    }
}
