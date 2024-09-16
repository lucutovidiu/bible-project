package ro.bible.yahwehtora.service;

import io.quarkus.logging.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.nodes.Document;
import ro.bible.util.BibleUtil;
import ro.bible.util.FileUtil;
import ro.bible.util.JSoapUtil;
import ro.bible.yahwehtora.YahwehtoraDocumentDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BibleSourceDocuments {

    public static final List<BookLocalPaths> booksPath = new ArrayList<>();

    static {
        booksPath.add(new BookLocalPaths("Genesa – Bereshith", "bible-source-documents/genesa", "genesa.html"));
        booksPath.add(new BookLocalPaths("Exod – Shemoth", "bible-source-documents/exod", "exod.html"));
        booksPath.add(new BookLocalPaths("Levitic – Wayiqra", "bible-source-documents/levitic", "levitic.html"));
        booksPath.add(new BookLocalPaths("Numeri – Bamidbar", "bible-source-documents/numeri", "numeri.html"));
        booksPath.add(new BookLocalPaths("Deuteronom – Devarim", "bible-source-documents/deuteronom", "deuteronom.html"));
        booksPath.add(new BookLocalPaths("Iosua – Yahoshua", "bible-source-documents/iosua", "iosua.html"));
        booksPath.add(new BookLocalPaths("Judecători – Shoftim", "bible-source-documents/judecatori", "judecatori.html"));
        booksPath.add(new BookLocalPaths("Rut – Rute", "bible-source-documents/rut", "rut.html"));
        booksPath.add(new BookLocalPaths("1 Samuel – Shmuel Alef", "bible-source-documents/1samuel", "1samuel.html"));
        booksPath.add(new BookLocalPaths("2 Samuel – Shmuel Bet", "bible-source-documents/2samuel", "2samuel.html"));
        booksPath.add(new BookLocalPaths("1 Împărați – Melechim Alef", "bible-source-documents/1imparati", "1imparati.html"));
        booksPath.add(new BookLocalPaths("2 Împărați – Melechim Bet", "bible-source-documents/2imparati", "2imparati.html"));
        booksPath.add(new BookLocalPaths("1 Cron. – Divre HaYamim Alef", "bible-source-documents/1cron", "1cron.html"));
        booksPath.add(new BookLocalPaths("2 Cron. – Divre HaYamim Bet", "bible-source-documents/2cron", "2cron.html"));
        booksPath.add(new BookLocalPaths("Ezra – Ezrah", "bible-source-documents/ezra", "ezra.html"));
        booksPath.add(new BookLocalPaths("Neemia – Nechemyah", "bible-source-documents/neemia", "neemia.html"));
        booksPath.add(new BookLocalPaths("Estera – Hadasa", "bible-source-documents/estera", "estera.html"));
        booksPath.add(new BookLocalPaths("Iov – Yowb", "bible-source-documents/iov", "iov.html"));
        booksPath.add(new BookLocalPaths("Psalmii – Tehillim", "bible-source-documents/psalmii", "psalmii.html"));
        booksPath.add(new BookLocalPaths("Prov. lui Solomon – Mashal", "bible-source-documents/prov", "prov.html"));
        booksPath.add(new BookLocalPaths("Eclesiastul – Koheleth", "bible-source-documents/eclesiastul", "eclesiastul.html"));
        booksPath.add(new BookLocalPaths("Cînt. Cîntări. – Shir HaShirim", "bible-source-documents/cint_cantarilor", "cint.html"));
        booksPath.add(new BookLocalPaths("Isaia – Yeshayahu", "bible-source-documents/isaia", "isaia.html"));
        booksPath.add(new BookLocalPaths("Ieremia – Yermeyahu", "bible-source-documents/ieremia", "ieremia.html"));
        booksPath.add(new BookLocalPaths("Plîngerile lui Ieremia – Ekah", "bible-source-documents/plingerile_lui_ieremia", "plingerile.html"));
        booksPath.add(new BookLocalPaths("Ezechiel – Yechezkel", "bible-source-documents/ezechiel", "ezechiel.html"));
        booksPath.add(new BookLocalPaths("Daniel – Danieyl", "bible-source-documents/daniel", "daniel.html"));
        booksPath.add(new BookLocalPaths("Osea – Hoshea", "bible-source-documents/osea", "osea.html"));
        booksPath.add(new BookLocalPaths("Ioel – Yah”El", "bible-source-documents/ioel", "ioel.html"));
        booksPath.add(new BookLocalPaths("Amos – Ahmos", "bible-source-documents/amos", "amos.html"));
        booksPath.add(new BookLocalPaths("Obadia – Obadyah", "bible-source-documents/obadia", "obadia.html"));
        booksPath.add(new BookLocalPaths("Iona – Yownah", "bible-source-documents/iona", "iona.html"));
        booksPath.add(new BookLocalPaths("Mica – Micha", "bible-source-documents/mica", "mica.html"));
        booksPath.add(new BookLocalPaths("Naum – Nachum", "bible-source-documents/naum", "naum.html"));
        booksPath.add(new BookLocalPaths("Habacuc – Chabakkuwk", "bible-source-documents/habacuc", "habacuc.html"));
        booksPath.add(new BookLocalPaths("Țefania – Tsephanyah", "bible-source-documents/tefania", "tefania.html"));
        booksPath.add(new BookLocalPaths("Hagai – Chaggay", "bible-source-documents/hagai", "hagai.html"));
        booksPath.add(new BookLocalPaths("Zaharia – Zecharyah", "bible-source-documents/zaharia", "zaharia.html"));
        booksPath.add(new BookLocalPaths("Maleahi – Malachi", "bible-source-documents/maleahi", "maleahi.html"));
        booksPath.add(new BookLocalPaths("Cartea lui Tobit – Tobiyah", "bible-source-documents/cartea_tobit", "cartea.html"));
        booksPath.add(new BookLocalPaths("Cartea Iuditei", "bible-source-documents/cartea_iuditei", "cartea.html"));
        booksPath.add(new BookLocalPaths("Cartea lui Baruh", "bible-source-documents/cartea_baruh", "cartea.html"));
        booksPath.add(new BookLocalPaths("Epistola lui Ieremia", "bible-source-documents/epistola_ieremia", "epistola.html"));
        booksPath.add(new BookLocalPaths("Cântarea celor trei tineri", "bible-source-documents/cantarea_celor_trei_tineri", "cantarea.html"));
        booksPath.add(new BookLocalPaths("Cartea a treia a lui Ezra", "bible-source-documents/cartea_ezra", "cartea.html"));
        booksPath.add(new BookLocalPaths("Cartea înțelep. lui Solomon", "bible-source-documents/cartea_solomon", "cartea.html"));
        booksPath.add(new BookLocalPaths("Istoria Susanei", "bible-source-documents/istoria_susanei", "istoria.html"));
        booksPath.add(new BookLocalPaths("Istoria omorârii balaurului", "bible-source-documents/istoria_balaurului", "istoria.html"));
        booksPath.add(new BookLocalPaths("1 Macabei", "bible-source-documents/1macabei", "1macabei.html"));
        booksPath.add(new BookLocalPaths("2 Macabei", "bible-source-documents/2macabei", "2macabei.html"));
        booksPath.add(new BookLocalPaths("3 Macabei", "bible-source-documents/3macabei", "3macabei.html"));
        booksPath.add(new BookLocalPaths("Rugăciunea regelui Manase", "bible-source-documents/rugaciunea_manase", "rugaciunea.html"));
        booksPath.add(new BookLocalPaths("Cartea lui Enoh", "bible-source-documents/cartea_enoh", "cartea.html"));
        booksPath.add(new BookLocalPaths("Matei – Mattityahu", "bible-source-documents/matei", "matei.html"));
        booksPath.add(new BookLocalPaths("Marcu – Moshe", "bible-source-documents/marcu", "marcu.html"));
        booksPath.add(new BookLocalPaths("Luca – Luka", "bible-source-documents/luca", "luca.html"));
        booksPath.add(new BookLocalPaths("Ioan – Yochanan", "bible-source-documents/ioan", "ioan.html"));
        booksPath.add(new BookLocalPaths("Fapte. Ap.- Maaseh Shlichim", "bible-source-documents/fapte", "fapte.html"));
        booksPath.add(new BookLocalPaths("Romani – Romiyah", "bible-source-documents/romani", "romani.html"));
        booksPath.add(new BookLocalPaths("1 Corinteni – Corintiyah Alef", "bible-source-documents/1corinteni", "1corinteni.html"));
        booksPath.add(new BookLocalPaths("2 Corinteni – Qorintiyah Bet", "bible-source-documents/2corinteni", "2corinteni.html"));
        booksPath.add(new BookLocalPaths("Galateni – Galutyah", "bible-source-documents/galateni", "galateni.html"));
        booksPath.add(new BookLocalPaths("Efeseni – Ephsiyah", "bible-source-documents/efeseni", "efeseni.html"));
        booksPath.add(new BookLocalPaths("Filipeni – Fylypsiyah", "bible-source-documents/filipeni", "filipeni.html"));
        booksPath.add(new BookLocalPaths("Coloseni – Qolesayah", "bible-source-documents/coloseni", "coloseni.html"));
        booksPath.add(new BookLocalPaths("1 Tesalon.- Tesalonikyah Alef", "bible-source-documents/1tesalon", "1tesalon.html"));
        booksPath.add(new BookLocalPaths("2 Tesalon. – Tesalonikyah Bet", "bible-source-documents/2tesalon", "2tesalon.html"));
        booksPath.add(new BookLocalPaths("1 Timotei – Timtheous Alef", "bible-source-documents/1timotei", "1timotei.html"));
        booksPath.add(new BookLocalPaths("2 Timotei – Timtheous Bet", "bible-source-documents/2timotei", "2timotei.html"));
        booksPath.add(new BookLocalPaths("Tit – Teitus", "bible-source-documents/tit", "tit.html"));
        booksPath.add(new BookLocalPaths("Filimon – Fileymon", "bible-source-documents/filimon", "filimon.html"));
        booksPath.add(new BookLocalPaths("Evrei – Ivrim", "bible-source-documents/evrei", "evrei.html"));
        booksPath.add(new BookLocalPaths("Iacov – Yaacov", "bible-source-documents/iacov", "iacov.html"));
        booksPath.add(new BookLocalPaths("1 Petru – Kepfa Alef", "bible-source-documents/1petru", "1petru.html"));
        booksPath.add(new BookLocalPaths("2 Petru – Kefa Bet", "bible-source-documents/2petru", "2petru.html"));
        booksPath.add(new BookLocalPaths("1 Ioan – Yochanan Alef", "bible-source-documents/1ioan", "1ioan.html"));
        booksPath.add(new BookLocalPaths("2 Ioan – Yochanan Bet", "bible-source-documents/2ioan", "2ioan.html"));
        booksPath.add(new BookLocalPaths("3 Ioan – Yochanan Gimel", "bible-source-documents/3ioan", "3ioan.html"));
        booksPath.add(new BookLocalPaths("Iuda – Yahudah", "bible-source-documents/iuda", "iuda.html"));
        booksPath.add(new BookLocalPaths("Apocalipsa – Gilyahna", "bible-source-documents/apocalipsa", "apocalipsa.html"));
        booksPath.add(new BookLocalPaths("menu", "bible-source-documents/menu", "menu.html"));
    }

    public Document getDocumentForBook(String bookName) {
        return booksPath.stream()
                .filter(bookPaths -> bookPaths.getBookName().equals(bookName))
                .findFirst()
                .flatMap(this::getSourceDocument)
                .map(JSoapUtil::stringToDocument)
                .orElse(null);
    }

    private Optional<String> getSourceDocument(BookLocalPaths bookLocalPaths) {
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
        booksPath.forEach(this::createLocalDocument);
    }

    private void createLocalDocument(BookLocalPaths bookLocalPaths) {
        Log.infof("Creating local document for book name: '%s'", bookLocalPaths.getBookName());
        if (FileUtil.doesFileExists(bookLocalPaths.getWritableFullPath())) {
            Log.infof("Local document already exists for book name: '%s'", bookLocalPaths.getBookName());
            return;
        }

        downloadDocumentFromMainRepository(bookLocalPaths)
                .ifPresent(htmlStringDocument -> saveDocumentToLocal(bookLocalPaths, htmlStringDocument));
    }

    private Optional<String> downloadDocumentFromMainRepository(BookLocalPaths bookLocalPaths) {
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

    private void saveDocumentToLocal(BookLocalPaths bookLocalPaths, String htmlStringDocument) {
        Log.infof("Saving local document for book name: '%s'", bookLocalPaths.getBookName());

        FileUtil.createFolderIfNotExists(bookLocalPaths.getWritableBasePath());
        FileUtil.writeContentToFile(bookLocalPaths.getWritableFullPath(), htmlStringDocument);
    }
}

@Data
@AllArgsConstructor
class BookLocalPaths {
    private String bookName;
    private String bookBaseFolderPath;
    private String bookFileName;

    public String getWritableFullPath() {
        return FileUtil.RESOURCE_FOLDER + File.separator + bookBaseFolderPath + File.separator + bookFileName;
    }

    public String getWritableBasePath() {
        return FileUtil.RESOURCE_FOLDER + File.separator + bookBaseFolderPath;
    }

    public String getReadablePath() {
        return bookBaseFolderPath + File.separator + bookFileName;
    }
}
