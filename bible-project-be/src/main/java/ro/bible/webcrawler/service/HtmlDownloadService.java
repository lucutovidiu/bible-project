package ro.bible.webcrawler.service;

import io.quarkus.logging.Log;
import lombok.experimental.UtilityClass;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Optional;

@UtilityClass
public class HtmlDownloadService {

    public Optional<String> downloadHtmlStringDocument(String documentPath) {
        return downloadDocument(documentPath).map(Document::html);
    }

    public Optional<Document> downloadDocument(String documentPath) {
        try {
            return Optional.of(Jsoup.connect(documentPath).get());
        } catch (IOException e) {
            Log.info("Could not connect to yahwehtora.");
        }

        return Optional.empty();
    }
}
