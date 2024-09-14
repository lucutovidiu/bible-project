package ro.bible.yahwehtora.service;

import io.quarkus.arc.All;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ro.bible.util.FileUtil;

import java.io.File;
import java.util.*;

import static org.jboss.resteasy.reactive.server.core.parameters.MultipartFormParamExtractor.Type.Path;

public class BibleSourceDocuments {

    private static final List<BookLocalPaths> booksPath = new ArrayList<>();
    static {
        booksPath.add(new BookLocalPaths("Genesa – Bereshith", "src/main/resources/bible-source-documents/genesa","genesa.html"));
        booksPath.add(new BookLocalPaths("Exod – Shemoth", "src/main/resources/bible-source-documents/exod","exod.html"));
        booksPath.add(new BookLocalPaths("Levitic – Wayiqra", "src/main/resources/bible-source-documents/levitic","levitic.html"));
        booksPath.add(new BookLocalPaths("Numeri – Bamidbar", "src/main/resources/bible-source-documents/numeri","numeri.html"));
        booksPath.add(new BookLocalPaths("Deuteronom – Devarim", "src/main/resources/bible-source-documents/deuteronom","deuteronom.html"));
        booksPath.add(new BookLocalPaths("Iosua – Yahoshua", "src/main/resources/bible-source-documents/iosua","iosua.html"));
        booksPath.add(new BookLocalPaths("Judecători – Shoftim", "src/main/resources/bible-source-documents/judecatori","judecatori.html"));
        booksPath.add(new BookLocalPaths("Rut – Rute", "src/main/resources/bible-source-documents/rut","rut.html"));
        booksPath.add(new BookLocalPaths("1 Samuel – Shmuel Alef", "src/main/resources/bible-source-documents/1samuel","1samuel.html"));
        booksPath.add(new BookLocalPaths("2 Samuel – Shmuel Bet", "src/main/resources/bible-source-documents/2samuel","2samuel.html"));
        booksPath.add(new BookLocalPaths("1 Împărați – Melechim Alef", "src/main/resources/bible-source-documents/1imparati","1imparati.html"));
        booksPath.add(new BookLocalPaths("2 Împărați – Melechim Bet", "src/main/resources/bible-source-documents/2imparati","2imparati.html"));
        booksPath.add(new BookLocalPaths("1 Cron. – Divre HaYamim Alef", "src/main/resources/bible-source-documents/1cron","1cron.html"));
        booksPath.add(new BookLocalPaths("2 Cron. – Divre HaYamim Bet", "src/main/resources/bible-source-documents/2cron","2cron.html"));
        booksPath.add(new BookLocalPaths("Ezra – Ezrah", "src/main/resources/bible-source-documents/ezra","ezra.html"));
        booksPath.add(new BookLocalPaths("Neemia – Nechemyah", "src/main/resources/bible-source-documents/neemia","neemia.html"));
        booksPath.add(new BookLocalPaths("Estera – Hadasa", "src/main/resources/bible-source-documents/estera","estera.html"));
        booksPath.add(new BookLocalPaths("Iov – Yowb", "src/main/resources/bible-source-documents/iov","iov.html"));
        booksPath.add(new BookLocalPaths("Psalmii – Tehillim", "src/main/resources/bible-source-documents/psalmii","psalmii.html"));
        booksPath.add(new BookLocalPaths("Prov. lui Solomon – Mashal", "src/main/resources/bible-source-documents/prov","prov.html"));
        booksPath.add(new BookLocalPaths("Eclesiastul – Koheleth", "src/main/resources/bible-source-documents/eclesiastul","eclesiastul.html"));
        booksPath.add(new BookLocalPaths("Cînt. Cîntări. – Shir HaShirim", "src/main/resources/bible-source-documents/cint_cantarilor","cint.html"));
        booksPath.add(new BookLocalPaths("Isaia – Yeshayahu", "src/main/resources/bible-source-documents/isaia","isaia.html"));
        booksPath.add(new BookLocalPaths("Ieremia – Yermeyahu", "src/main/resources/bible-source-documents/ieremia","ieremia.html"));
        booksPath.add(new BookLocalPaths("Plîngerile lui Ieremia – Ekah", "src/main/resources/bible-source-documents/plingerile_lui_ieremia","plingerile.html"));
        booksPath.add(new BookLocalPaths("Ezechiel – Yechezkel", "src/main/resources/bible-source-documents/ezechiel","ezechiel.html"));
        booksPath.add(new BookLocalPaths("Daniel – Danieyl", "src/main/resources/bible-source-documents/daniel","daniel.html"));
        booksPath.add(new BookLocalPaths("Osea – Hoshea", "src/main/resources/bible-source-documents/osea","osea.html"));
        booksPath.add(new BookLocalPaths("Ioel – Yah”El", "src/main/resources/bible-source-documents/ioel","ioel.html"));
        booksPath.add(new BookLocalPaths("Amos – Ahmos", "src/main/resources/bible-source-documents/amos","amos.html"));
        booksPath.add(new BookLocalPaths("Obadia – Obadyah", "src/main/resources/bible-source-documents/obadia","obadia.html"));
        booksPath.add(new BookLocalPaths("Iona – Yownah", "src/main/resources/bible-source-documents/iona","iona.html"));
        booksPath.add(new BookLocalPaths("Mica – Micha", "src/main/resources/bible-source-documents/mica","mica.html"));
        booksPath.add(new BookLocalPaths("Naum – Nachum", "src/main/resources/bible-source-documents/naum","naum.html"));
        booksPath.add(new BookLocalPaths("Habacuc – Chabakkuwk", "src/main/resources/bible-source-documents/habacuc","habacuc.html"));
        booksPath.add(new BookLocalPaths("Țefania – Tsephanyah", "src/main/resources/bible-source-documents/tefania","tefania.html"));
        booksPath.add(new BookLocalPaths("Hagai – Chaggay", "src/main/resources/bible-source-documents/hagai","hagai.html"));
        booksPath.add(new BookLocalPaths("Zaharia – Zecharyah", "src/main/resources/bible-source-documents/zaharia","zaharia.html"));
        booksPath.add(new BookLocalPaths("Maleahi – Malachi", "src/main/resources/bible-source-documents/maleahi","maleahi.html"));
        booksPath.add(new BookLocalPaths("Cartea lui Tobit – Tobiyah", "src/main/resources/bible-source-documents/cartea_tobit","cartea.html"));
        booksPath.add(new BookLocalPaths("Cartea Iuditei", "src/main/resources/bible-source-documents/cartea_iuditei","cartea.html"));
        booksPath.add(new BookLocalPaths("Cartea lui Baruh", "src/main/resources/bible-source-documents/cartea_baruh","cartea.html"));
        booksPath.add(new BookLocalPaths("Epistola lui Ieremia", "src/main/resources/bible-source-documents/epistola_ieremia","epistola.html"));
        booksPath.add(new BookLocalPaths("Cântarea celor trei tineri", "src/main/resources/bible-source-documents/cantarea_celor_trei_tineri","cantarea.html"));
        booksPath.add(new BookLocalPaths("Cartea a treia a lui Ezra", "src/main/resources/bible-source-documents/cartea_ezra","cartea.html"));
        booksPath.add(new BookLocalPaths("Cartea înțelep. lui Solomon", "src/main/resources/bible-source-documents/cartea_solomon","cartea.html"));
        booksPath.add(new BookLocalPaths("Istoria Susanei", "src/main/resources/bible-source-documents/istoria_susanei","istoria.html"));
        booksPath.add(new BookLocalPaths("Istoria omorârii balaurului", "src/main/resources/bible-source-documents/istoria_balaurului","istoria.html"));
        booksPath.add(new BookLocalPaths("1 Macabei", "src/main/resources/bible-source-documents/1macabei","1macabei.html"));
        booksPath.add(new BookLocalPaths("2 Macabei", "src/main/resources/bible-source-documents/2macabei","2macabei.html"));
        booksPath.add(new BookLocalPaths("3 Macabei", "src/main/resources/bible-source-documents/3macabei","3macabei.html"));
        booksPath.add(new BookLocalPaths("Rugăciunea regelui Manase", "src/main/resources/bible-source-documents/rugaciunea_manase","rugaciunea.html"));
        booksPath.add(new BookLocalPaths("Cartea lui Enoh", "src/main/resources/bible-source-documents/cartea_enoh","cartea.html"));
        booksPath.add(new BookLocalPaths("Matei – Mattityahu", "src/main/resources/bible-source-documents/matei","matei.html"));
        booksPath.add(new BookLocalPaths("Marcu – Moshe", "src/main/resources/bible-source-documents/marcu","marcu.html"));
        booksPath.add(new BookLocalPaths("Luca – Luka", "src/main/resources/bible-source-documents/luca","luca.html"));
        booksPath.add(new BookLocalPaths("Ioan – Yochanan", "src/main/resources/bible-source-documents/ioan","ioan.html"));
        booksPath.add(new BookLocalPaths("Fapte. Ap.- Maaseh Shlichim", "src/main/resources/bible-source-documents/fapte","fapte.html"));
        booksPath.add(new BookLocalPaths("Romani – Romiyah", "src/main/resources/bible-source-documents/romani","romani.html"));
        booksPath.add(new BookLocalPaths("1 Corinteni – Corintiyah Alef", "src/main/resources/bible-source-documents/1corinteni","1corinteni.html"));
        booksPath.add(new BookLocalPaths("2 Corinteni – Qorintiyah Bet", "src/main/resources/bible-source-documents/2corinteni","2corinteni.html"));
        booksPath.add(new BookLocalPaths("Galateni – Galutyah", "src/main/resources/bible-source-documents/galateni","galateni.html"));
        booksPath.add(new BookLocalPaths("Efeseni – Ephsiyah", "src/main/resources/bible-source-documents/efeseni","efeseni.html"));
        booksPath.add(new BookLocalPaths("Filipeni – Fylypsiyah", "src/main/resources/bible-source-documents/filipeni","filipeni.html"));
        booksPath.add(new BookLocalPaths("Coloseni – Qolesayah", "src/main/resources/bible-source-documents/coloseni","coloseni.html"));
        booksPath.add(new BookLocalPaths("1 Tesalon.- Tesalonikyah Alef", "src/main/resources/bible-source-documents/1tesalon","1tesalon.html"));
        booksPath.add(new BookLocalPaths("2 Tesalon. – Tesalonikyah Bet", "src/main/resources/bible-source-documents/2tesalon","2tesalon.html"));
        booksPath.add(new BookLocalPaths("1 Timotei – Timtheous Alef", "src/main/resources/bible-source-documents/1timotei","1timotei.html"));
        booksPath.add(new BookLocalPaths("2 Timotei – Timtheous Bet", "src/main/resources/bible-source-documents/2timotei","2timotei.html"));
        booksPath.add(new BookLocalPaths("Tit – Teitus", "src/main/resources/bible-source-documents/tit","tit.html"));
        booksPath.add(new BookLocalPaths("Filimon – Fileymon", "src/main/resources/bible-source-documents/filimon","filimon.html"));
        booksPath.add(new BookLocalPaths("Evrei – Ivrim", "src/main/resources/bible-source-documents/evrei","evrei.html"));
        booksPath.add(new BookLocalPaths("Iacov – Yaacov", "src/main/resources/bible-source-documents/iacov","iacov.html"));
        booksPath.add(new BookLocalPaths("1 Petru – Kepfa Alef", "src/main/resources/bible-source-documents/1petru","1petru.html"));
        booksPath.add(new BookLocalPaths("2 Petru – Kefa Bet", "src/main/resources/bible-source-documents/2petru","2petru.html"));
        booksPath.add(new BookLocalPaths("1 Ioan – Yochanan Alef", "src/main/resources/bible-source-documents/1ioan","1ioan.html"));
        booksPath.add(new BookLocalPaths("2 Ioan – Yochanan Bet", "src/main/resources/bible-source-documents/2ioan","2ioan.html"));
        booksPath.add(new BookLocalPaths("3 Ioan – Yochanan Gimel", "src/main/resources/bible-source-documents/3ioan","3ioan.html"));
        booksPath.add(new BookLocalPaths("Iuda – Yahudah", "src/main/resources/bible-source-documents/iuda","iuda.html"));
        booksPath.add(new BookLocalPaths("Apocalipsa – Gilyahna", "src/main/resources/bible-source-documents/apocalipsa","apocalipsa.html"));    }

    public void downloadAllRequiredHtmlSources() {
        booksPath.forEach((bookLocalPaths) -> {
           FileUtil.createFolderIfNotExists(bookLocalPaths.getBookBaseFolderPath());
           FileUtil.createFileIfNotExists(bookLocalPaths.getBookBaseFolderPath() + File.separator + bookLocalPaths.getBookFileName());
        });
    }

    public Optional<Document> getDocumentFromResource(String bookName) {
        if(!FileUtil.doesFileExists(getBookPath(bookName))) {
            return Optional.empty();
        }

        return FileUtil.getFileFromClasspath("bible-source-documents/htmlPage.html")
                .map(Jsoup::parse);
    }

    public String getBookPath(String bookName) {
        return "";
    }
}

@Data
@AllArgsConstructor
class BookLocalPaths {
    private String bookName;
    private String bookBaseFolderPath;
    private String bookFileName;
}
