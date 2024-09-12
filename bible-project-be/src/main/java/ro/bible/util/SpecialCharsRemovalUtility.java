package ro.bible.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpecialCharsRemovalUtility {

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

    public String removeSpecialChars(String text) {
        return text
                .replaceAll("\\*", "")
                .replaceAll("♦", "");
    }
}
