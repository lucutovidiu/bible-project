package ro.bible.shared.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

import static ro.bible.reporting.service.BookReportingService.DATE_TIME_FORMATTER;

@UtilityClass
public class BibleDateUtil {
    public String getDateNowFormatted() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }
}
