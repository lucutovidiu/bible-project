package ro.bible.localfiles.util;

import lombok.experimental.UtilityClass;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@UtilityClass
public class JSoapUtil {

    public Document stringToDocument(String string) {
        return Jsoup.parse(string);
    }
}
