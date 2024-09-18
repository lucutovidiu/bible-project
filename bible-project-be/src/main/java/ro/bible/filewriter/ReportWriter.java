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
    public static final String REPORT_BASE_PATH_FULL_REPORT = "reports/full-report";
    public static final String REPORT_BASE_PATH_MIGRATION_REPORT = "reports/migration-report";
    public static final String REPORT_PRE_EXTENSION = "full-report-data-";
    private static final String fileSeparator = File.separator;
    private static String reportFilePath;

    public ReportWriter() {
//        FileUtil.createFolderIfNotExists(REPORT_BASE_PATH_FULL_REPORT);
//        reportFilePath = REPORT_BASE_PATH_FULL_REPORT + fileSeparator + REPORT_PRE_EXTENSION + getDateNowFormatted() + ".txt";
//        FileUtil.createFileIfNotExists(REPORT_BASE_PATH_FULL_REPORT, REPORT_PRE_EXTENSION + getDateNowFormatted() + ".txt");
    }

    public ReportWriter(String appendToReportName) {
//        FileUtil.createFolderIfNotExists(REPORT_BASE_PATH_MIGRATION_REPORT);
//        reportFilePath = REPORT_BASE_PATH_MIGRATION_REPORT + fileSeparator + appendToReportName + ".txt";
//        FileUtil.createFileIfNotExists(REPORT_BASE_PATH_MIGRATION_REPORT, appendToReportName + ".txt");
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
        // todo this is not writing to correct resource folder but the one inside the target
//        try (PrintWriter writer = getWriter(reportFilePath)) {
//            writer.println(line);
//        }
        System.out.println(line);
    }
}
