package ro.bible.importexport.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.importexport.service.BookExporterService;
import ro.bible.importexport.service.YamlExporterService;
import ro.bible.persistence.model.BookPojo;
import ro.bible.persistence.model.ChapterPojo;
import ro.bible.persistence.model.VersePojo;
import ro.bible.persistence.service.BookService;
import ro.bible.shared.service.YamlMapperService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class YamlBookExporterService implements BookExporterService {
    @Inject
    BookService bookService;
    @Inject
    YamlMapperService yamlMapperService;
    @Inject
    YamlExporterService yamlExporterService;

    @Override
    public void exportAllBooks() {
        Log.info("Exporting All Books");
        bookService.getAllBooks().forEach(bookPojo -> exportBook(bookPojo.getName()));
    }

    @Override
    public void exportBook(String bookName) {
        Optional<BookPojo> entireBookByName = bookService.getEntireBookByName(bookName);
        if (entireBookByName.isPresent()) {
            BookPojo bookPojo = entireBookByName.get();
            orderChaptersAndVerses(bookPojo);
//            replaceWords(bookPojo);
            convertToYaml(bookPojo)
                    .ifPresentOrElse(bookContent -> exportYamlBook(bookPojo, bookContent), () ->
                            Log.errorf("Book: '%s', could not be converted", bookName)
                    );
        } else {
            Log.errorf("Book: '%s', not found in the database", bookName);
        }
    }

    private void orderChaptersAndVerses(BookPojo bookPojo) {
        bookPojo.getChapterPojo().forEach(chapterPojo -> {
            List<VersePojo> list = chapterPojo.getVersePojo().stream()
                    .sorted(Comparator.comparing(VersePojo::getVerseNumber))
                    .toList();
            chapterPojo.setVersePojo(list);
        });

        List<ChapterPojo> list = bookPojo.getChapterPojo().stream()
                .sorted(Comparator.comparing(ChapterPojo::getNumber)).toList();

        bookPojo.setChapterPojo(list);
    }

//    private void replaceWords(BookPojo bookPojo) {
//        bookPojo.getChapterPojo().forEach(chapterPojo -> {
//            List<VersePojo> list = chapterPojo.getVersePojo().stream()
//                    .map(versePojo -> {
//                        versePojo.setText(
//                                rePlaceWords(versePojo.getText())
//                        );
//                        versePojo.setTextWithDiacritics(
//                                rePlaceWords(versePojo.getTextWithDiacritics())
//                        );
//                        return versePojo;
//                    }).toList();
//            chapterPojo.setVersePojo(list);
//        });
//
//
//    }
//
//    private String rePlaceWords(String verse) {
//        String s1 = replaceCaseInsensitive(verse, "YaHWeH", "Yaue");
//        return replaceCaseInsensitive(s1, "Eylohim", "Elohim");
//    }

    private Optional<String> convertToYaml(BookPojo bookPojo) {
        return yamlMapperService.objectToString(bookPojo);
    }

    private void exportYamlBook(BookPojo bookPojo, String bookContent) {
        yamlExporterService.exportYaml(bookPojo, bookContent);
    }

}
