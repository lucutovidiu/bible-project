package ro.bible.reporting.service;

import ro.bible.shared.util.BibleDateUtil;
import ro.bible.shared.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class FileReportWriterService {
    public static final String REPORT_BASE_PATH_FULL_REPORT = "src/main/resources/reports/full-report";
    public static final String REPORT_BASE_PATH_MIGRATION_REPORT = "src/main/resources/reports/migration-report";
    public static final String REPORT_PRE_EXTENSION = "full-report-data-";
    private static final String fileSeparator = File.separator;
    private final String activeProfile;
    private String reportFilePath;

    public FileReportWriterService(String activeProfile) {
        this.activeProfile = activeProfile;
        prepareReport(null);
    }


    private void prepareReport(String appendToReportName) {
        if (activeProfile.equals("development")) {
            if (appendToReportName != null) {
                FileUtil.createFolderIfNotExists(REPORT_BASE_PATH_MIGRATION_REPORT);
                reportFilePath = REPORT_BASE_PATH_MIGRATION_REPORT + fileSeparator + appendToReportName + ".txt";
                FileUtil.createFileIfNotExists(reportFilePath);
            } else {
                FileUtil.createFolderIfNotExists(REPORT_BASE_PATH_FULL_REPORT);
                reportFilePath = REPORT_BASE_PATH_FULL_REPORT + fileSeparator + REPORT_PRE_EXTENSION + BibleDateUtil.getDateNowFormatted() + ".txt";
                FileUtil.createFileIfNotExists(reportFilePath);
            }
        }
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
        if (activeProfile.equals("development")) {
            try (PrintWriter writer = getWriter(reportFilePath)) {
                writer.println(line);
                return;
            }
        }
    }
}
