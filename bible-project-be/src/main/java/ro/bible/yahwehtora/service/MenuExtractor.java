package ro.bible.yahwehtora.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import ro.bible.yahwehtora.YahwehtoraDocumentDownloader;
import ro.bible.yahwehtora.dto.BibleBookLink;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class MenuExtractor {

    public static List<BibleBookLink> extractBibleMenus() throws IOException {
        BibleSourceDocuments bibleSourceDocuments = new BibleSourceDocuments();
        Document doc = bibleSourceDocuments.getDocumentForBook("menu");
        return doc.select(".menu-item a")
                .stream().map((a) -> new BibleBookLink(extractBookNameRegex(extractBookMenuNames(a.childNodes())), a.attr("href"))
                ).collect(toList());
    }

    private static String extractBookMenuNames(List<Node> nodes) {
        Node element = null;
        while (!nodes.isEmpty()) {
            element = nodes.get(0);
            nodes = element.childNodes();
        }

        return element != null ? element.toString() : null;
    }

    private static String extractBookNameRegex(String input) {
        // Regex to match "digit ] " and capture everything after it
        String regex = "\\d+\\s*]\\s*(.*)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            // Extract the part after "01 ]"
            return matcher.group(1);
        }

        return null;
    }
}
