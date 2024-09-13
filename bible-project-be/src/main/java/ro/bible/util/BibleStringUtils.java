package ro.bible.util;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BibleStringUtils {

    public String normalizeStringForDb(String input) {
        return normaliseString(removeSpecialChars(removeHebrewText(input))).trim();
    }

    public String removeHebrewText(String text) {
        return text.replaceAll("[\\u0590-\\u05FF]", "").trim();
    }

    public String normaliseString(String text) {
        return text.replaceAll("\\R+", " ")
                .replaceAll("-", "")
                .replaceAll(" {2,}", " ");
    }

    public String removeSpecialChars(String text) {
        return text
                .replaceAll("\\*", "")
                .replaceAll("♦", "");
    }
}
