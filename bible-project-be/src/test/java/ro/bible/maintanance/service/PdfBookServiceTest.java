package ro.bible.maintanance.service;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PdfBookServiceTest {

    @Inject
    PdfBookService pdfBookService;

    @Test
    public void printPdf() {
        pdfBookService.migratePdfBook();
    }

}
