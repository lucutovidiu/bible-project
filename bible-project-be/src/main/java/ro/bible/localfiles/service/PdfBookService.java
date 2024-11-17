package ro.bible.localfiles.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import ro.bible.htmlparser.service.ChapterExtractorService;

import java.io.File;
import java.io.IOException;
import java.util.*;

@ApplicationScoped
public class PdfBookService {

    public static Map<String, List<String>> extractChapterVerses(String filePath) throws IOException {
        Map<String, List<String>> bibleChapters = new HashMap<>();

        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            String[] lines = text.split("\r\n|\r|\n"); // Split text by lines
            String currentChapter = null;
            List<String> currentVerses = new ArrayList<>();

            for (String line : lines) {
                line = line.trim();

                // Check if the line indicates a new chapter, e.g., "Capitolul 1"
                if (line.matches("Capitolul\\s+\\d+")) {
                    // Save the previous chapter and its verses, if any
                    if (currentChapter != null) {
                        bibleChapters.put(currentChapter, new ArrayList<>(currentVerses));
                    }

                    // Start a new chapter
                    currentChapter = line;
                    currentVerses.clear();
                } else if (currentChapter != null) {
                    // This is a verse under the current chapter
                    currentVerses.add(line);
                }
            }

            // Add the last chapter after loop
            if (currentChapter != null) {
                bibleChapters.put(currentChapter, new ArrayList<>(currentVerses));
            }
        }

        return bibleChapters;
    }

    public static List<String> getVersesByChapter(String filePath, int chapterNumber) throws IOException {
        Map<String, List<String>> chapters = extractChapterVerses(filePath);
        String chapterKey = "Capitolul " + chapterNumber;

        return chapters.getOrDefault(chapterKey, new ArrayList<>());
    }

    public void migratePdfBook() {
        String pdfPath = "src/main/resources/bible-source-documents/carti_pdf/cartea_dreptului/Cartea Dreptului.pdf";
        try {
            int chapterNumber = 1;

            List<String> verses = getVersesByChapter(pdfPath, chapterNumber);
            ChapterExtractorService extractor = new ChapterExtractorService("", "", "");
            Map<Integer, String> integerStringMap = extractor.extractVersesNumbersAndText(verses);
            System.out.println(integerStringMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
