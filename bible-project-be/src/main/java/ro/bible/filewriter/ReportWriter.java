package ro.bible.filewriter;

import ro.bible.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static ro.bible.maintanance.service.BookReportingService.DATE_TIME_FORMATTER;

public class ReportWriter {
    private static final String basePathPath = "src/main/resources/reports";
    private static final String fileSeparator = File.separator;
    private static String reportFilePath;

    public ReportWriter() {
        FileUtil.createFolderIfNotExists(basePathPath);
        reportFilePath = basePathPath + fileSeparator + "full-report-data-" + getDateNowFormatted() + ".txt";
        FileUtil.createFileIfNotExists(reportFilePath);
    }

    public ReportWriter(String appendToReportName) {
        FileUtil.createFolderIfNotExists(basePathPath);
        reportFilePath = basePathPath + fileSeparator + appendToReportName + ".txt";
        FileUtil.createFileIfNotExists(reportFilePath);
    }

    public String getDateNowFormatted() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
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
        try (PrintWriter writer = getWriter(reportFilePath)) {
            writer.println(line);
        }
    }
}
