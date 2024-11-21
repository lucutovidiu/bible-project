package ro.bible.maintanance.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import ro.bible.localfiles.service.PdfBookService;

@QuarkusTest
class PdfBookServiceTest {

    @Inject
    PdfBookService pdfBookService;

    @Test
    public void printPdf() {
//        pdfBookService.migratePdfBook();
    }

}
