package ro.bible.yahwehtora.service;

import io.quarkus.logging.Log;
import org.jsoup.nodes.Document;
import ro.bible.util.BibleUtil;
import ro.bible.util.FileUtil;
import ro.bible.util.JSoapUtil;
import ro.bible.yahwehtora.YahwehtoraDocumentDownloader;
import ro.bible.yahwehtora.dto.BookInfo;

import java.util.Optional;

public class BibleSourceDocuments {

    public Document getDocumentForBook(String bookName) {
        return BibleUtil.getBookInfoList().stream()
                .filter(bookPaths -> bookPaths.getBookName().equals(bookName))
                .findFirst()
                .flatMap(this::getSourceDocument)
                .map(JSoapUtil::stringToDocument)
                .orElse(null);
    }

    private Optional<String> getSourceDocument(BookInfo bookLocalPaths) {
        if (FileUtil.doesFileExists(bookLocalPaths.getWritableFullPath())) {
            Log.info("Getting local document for book" + bookLocalPaths.getBookName());

            return FileUtil.getFileFromClasspath(bookLocalPaths.getReadablePath());
        }
        Log.infof("Document NOT found Locally for path: '%s' for book: '%s' but trying remotely", bookLocalPaths.getWritableFullPath(), bookLocalPaths.getBookName());
        return downloadDocumentFromMainRepository(bookLocalPaths)
                .map(htmlStringDocument -> {
                    saveDocumentToLocal(bookLocalPaths, htmlStringDocument);
                    return htmlStringDocument;
                });
    }

    public void createAllRequiredHtmlLocalSources() {
        Log.info("Creating local documents for all books...");
        BibleUtil.getBookInfoList().forEach(this::createLocalDocument);
    }

    private void createLocalDocument(BookInfo bookLocalPaths) {
        Log.infof("Creating local document for book name: '%s'", bookLocalPaths.getBookName());
        if (FileUtil.doesFileExists(bookLocalPaths.getWritableFullPath())) {
            Log.infof("Local document already exists for book name: '%s'", bookLocalPaths.getBookName());
            return;
        }

        downloadDocumentFromMainRepository(bookLocalPaths)
                .ifPresent(htmlStringDocument -> saveDocumentToLocal(bookLocalPaths, htmlStringDocument));
    }

    private Optional<String> downloadDocumentFromMainRepository(BookInfo bookLocalPaths) {
        Log.infof("Downloading local document for book name: '%s'", bookLocalPaths.getBookName());

        try {
            Optional<String> htmlStringDocument = YahwehtoraDocumentDownloader
                    .downloadHtmlStringDocument(
                            BibleUtil.getBookInfoByBookName(bookLocalPaths.getBookName()).getDownloadUrl());
            Log.infof("Downloaded local document for book name: '%s'", bookLocalPaths.getBookName());

            return htmlStringDocument;
        } catch (Exception e) {
            Log.errorf("Fail to Download local document for book name: '%s'", bookLocalPaths.getBookName());
        }

        return Optional.empty();
    }

    private void saveDocumentToLocal(BookInfo bookLocalPaths, String htmlStringDocument) {
        Log.infof("Saving local document for book name: '%s'", bookLocalPaths.getBookName());

        FileUtil.createFolderIfNotExists(bookLocalPaths.getWritableBasePath());
        FileUtil.writeContentToFile(bookLocalPaths.getWritableFullPath(), htmlStringDocument);
    }
}
