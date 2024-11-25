package ro.bible.htmlparser.util;
import lombok.experimental.UtilityClass;

import java.text.Bidi;
import java.util.regex.Pattern;

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
                .replaceAll("-{2,}", "")
                .replaceAll(" {2,}", " ");
    }

    public String removeSpecialChars(String text) {
        return text
                .replaceAll("\\*", "")
                .replaceAll("♦", "");
    }

    public String forceLeftToRight(String text) {
        // Create a Bidi object with the input text and left-to-right flag
        Bidi bidi = new Bidi(text, Bidi.DIRECTION_LEFT_TO_RIGHT);

        // Check if the text needs reordering
        if (bidi.isLeftToRight()) {
            // If already left-to-right, no changes needed
            return text;
        } else {
            // Force left-to-right by embedding the text within LTR marks
            return "\u202A" + text + "\u202C";  // LTR embedding marks
        }
    }

    public String removeDiacritics(String text) {
        return text
                .replaceAll("Ă", "A")
                .replaceAll("ă", "a")
                .replaceAll("Â", "A")
                .replaceAll("â", "a")
                .replaceAll("Î", "I")
                .replaceAll("î", "i")
                .replaceAll("Ș", "S")
                .replaceAll("Ş", "S")
                .replaceAll("ș", "s")
                .replaceAll("ş", "s")
                .replaceAll("Ț", "T")
                .replaceAll("Ţ", "T")
                .replaceAll("ț", "t")
                .replaceAll("ţ", "t");
    }

    public static String replaceCaseInsensitive(String input, String target, String replacement) {
        return Pattern.compile(target, Pattern.CASE_INSENSITIVE)
                .matcher(input)
                .replaceAll(replacement);
    }
}
