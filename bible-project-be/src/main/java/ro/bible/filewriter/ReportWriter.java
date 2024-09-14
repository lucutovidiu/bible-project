package ro.bible.filewriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static ro.bible.maintanance.service.BookReportingService.DATE_TIME_FORMATTER;

public class ReportWriter {
    private static final String basePathPath = "/Users/ovidiulucut/Desktop/BibleReport";
    private static final String fileSeparator = File.separator;
    private static String reportFilePath;

    public ReportWriter() {
        createFolderIfNotExists(basePathPath);
        reportFilePath = basePathPath + fileSeparator + "report-data-"+LocalDateTime.now().format(DATE_TIME_FORMATTER)+".txt";
        createFileIfNotExists(reportFilePath);
    }

    private PrintWriter getWriter(String filePath) {
        try {
            File file = Paths.get(filePath).toFile();
            return new PrintWriter(new FileWriter(file, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLine(String line) {
        System.out.println("writing line: " + line);

        try (PrintWriter writer = getWriter(reportFilePath)) {
            writer.println(line);
        }
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
