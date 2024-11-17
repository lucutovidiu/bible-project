package ro.bible.localfiles.util;

import io.quarkus.logging.Log;
import org.jsoup.nodes.Document;
import ro.bible.shared.util.FileUtil;
import ro.bible.webcrawler.service.HtmlDownloadService;
import ro.bible.shared.model.BookInfo;

import java.util.Optional;

public class BibleSourceDocuments {

    public Document getDocumentForBook(String bookName) {
        Optional<BookInfo> bookInfoOptional = BibleUtil.getBookInfoList().stream()
                .filter(bookPaths -> bookPaths.getBookName().equals(bookName))
                .findFirst();

        if (bookInfoOptional.isPresent()) {
            Optional<String> sourceDocument = getSourceLocalDocument(bookInfoOptional.get());
            if (sourceDocument.isPresent()) {
                return JSoapUtil.stringToDocument(sourceDocument.get());
            } else {
                Log.errorf("Document not found locally for book: '%s'", bookName);
                Optional<Document> documentFromMainRepository = getSourceRemoteDocument(bookInfoOptional.get());
                if (documentFromMainRepository.isPresent()) {
                    // todo see how to do this maybe write to a different location or only in dev mode not prod as it writes to target folder
//                    saveDocumentToLocal(bookInfoOptional.get(), documentFromMainRepository.get().html());
                    return documentFromMainRepository.get();
                }
            }
        }

        return null;
    }

    private Optional<String> getSourceLocalDocument(BookInfo bookLocalPaths) {
        Optional<String> fileFromClasspath = FileUtil.getFileFromClasspath(bookLocalPaths.getFilePath());
        if (fileFromClasspath.isPresent()) {
            Log.infof("Getting local document for book: '%s'", bookLocalPaths.getBookName());

            return fileFromClasspath;
        }

        Log.infof("Local document for book: '%s' not found", bookLocalPaths.getBookName());
        return Optional.empty();
    }

    private Optional<Document> getSourceRemoteDocument(BookInfo bookLocalPaths) {
        Log.infof("Trying to source document for book: '%s' remotely", bookLocalPaths.getBookName());
        Optional<String> documentFromMainRepository = downloadDocumentFromMainRepository(bookLocalPaths);

        return documentFromMainRepository.map(JSoapUtil::stringToDocument);

    }

    // todo see how to do this maybe write to a different location or only in dev mode not prod as it writes to target folder
    /*
    public void createAllRequiredHtmlLocalSources() {
        Log.info("Creating local documents for all books...");
        BibleUtil.getBookInfoList().forEach(this::createLocalDocument);
    }

    private void createLocalDocument(BookInfo bookLocalPaths) {
        Log.infof("Creating local document for book name: '%s'", bookLocalPaths.getBookName());
        if (FileUtil.doesFileExists(bookLocalPaths.getBaseFolderPath())) {
            Log.infof("Local document already exists for book name: '%s'", bookLocalPaths.getBookName());
            return;
        }

        downloadDocumentFromMainRepository(bookLocalPaths)
                .ifPresent(htmlStringDocument -> saveDocumentToLocal(bookLocalPaths, htmlStringDocument));
    }

    private void saveDocumentToLocal(BookInfo bookLocalPaths, String htmlStringDocument) {
        Log.infof("Saving local document for book name: '%s'", bookLocalPaths.getBookName());

        FileUtil.createFolderIfNotExists(bookLocalPaths.getBaseFolderPath());
        FileUtil.writeContentToFile(bookLocalPaths.getFilePath(), htmlStringDocument);
    }
     */
    private Optional<String> downloadDocumentFromMainRepository(BookInfo bookLocalPaths) {
        Log.infof("Downloading local document for book name: '%s'", bookLocalPaths.getBookName());

        try {
            Optional<String> htmlStringDocument = HtmlDownloadService
                    .downloadHtmlStringDocument(
                            BibleUtil.getBookInfoByBookName(bookLocalPaths.getBookName()).getDownloadUrl());
            Log.infof("Downloaded local document for book name: '%s'", bookLocalPaths.getBookName());

            return htmlStringDocument;
        } catch (Exception e) {
            Log.errorf("Fail to Download local document for book name: '%s'", bookLocalPaths.getBookName());
        }

        return Optional.empty();
    }
}
