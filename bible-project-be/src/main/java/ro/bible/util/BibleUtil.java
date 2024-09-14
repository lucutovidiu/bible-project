package ro.bible.util;

import lombok.experimental.UtilityClass;
import ro.bible.yahwehtora.dto.BookInfo;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BibleUtil {

    public final List<BookInfo> bookInfoList = new ArrayList<>();
//https://www.biblememorygoal.com/how-many-chapters-verses-in-the-bible/
    static {
    bookInfoList.add(new BookInfo("Genesa – Bereshith",1, "Fac.", "Vechiul Testament", 50, 1533,"https://yahwehtora.ro/genesa-bereshith"));
    bookInfoList.add(new BookInfo("Exod – Shemoth", 2,"Exod.", "Vechiul Testament", 40, 1213,"https://yahwehtora.ro/exod-shemoth"));
    bookInfoList.add(new BookInfo("Levitic – Wayiqra", 3,"Lev.", "Vechiul Testament", 27, 859,"https://yahwehtora.ro/levitic-wayiqra"));
    bookInfoList.add(new BookInfo("Numeri – Bamidbar",4, "Num.", "Vechiul Testament", 36, 1288,"https://yahwehtora.ro/numeri-bamidbar"));
    bookInfoList.add(new BookInfo("Deuteronom – Devarim", 5,"Deut.", "Vechiul Testament", 34, 959,"https://yahwehtora.ro/deuteronom-devarim"));
    bookInfoList.add(new BookInfo("Iosua – Yahoshua",6, "Iosua.", "Vechiul Testament", 24, 658, "https://yahwehtora.ro/iosua-yahoshua"));
    bookInfoList.add(new BookInfo("Judecători – Shoftim",7, "Judec.", "Vechiul Testament", 21, 618,"https://yahwehtora.ro/judecatori-shoftim"));
    bookInfoList.add(new BookInfo("Rut – Rute",8, "Rut.", "Vechiul Testament", 4, 85, "https://yahwehtora.ro/rut-rute"));
    bookInfoList.add(new BookInfo("1 Samuel – Shmuel Alef",9, "1Sam.", "Vechiul Testament", 31, 810,"https://yahwehtora.ro/1-samuel-shmuel-alef"));
    bookInfoList.add(new BookInfo("2 Samuel – Shmuel Bet", 10,"2Sam.", "Vechiul Testament", 24, 695, "https://yahwehtora.ro/2-samuel-shmuel-bet"));
    bookInfoList.add(new BookInfo("1 Împărați – Melechim Alef",11, "1Reg.", "Vechiul Testament", 22, 816,"https://yahwehtora.ro/1-imparati-melechim-alef"));
    bookInfoList.add(new BookInfo("2 Împărați – Melechim Bet", 12,"2Reg.", "Vechiul Testament", 25, 719,"https://yahwehtora.ro/2-imparati-melechim-bet"));
    bookInfoList.add(new BookInfo("1 Cron. – Divre HaYamim Alef", 13,"1Cron.", "Vechiul Testament", 29, 942,"https://yahwehtora.ro/1-cronici-divre-hayamim-alef"));
    bookInfoList.add(new BookInfo("2 Cron. – Divre HaYamim Bet", 14,"2Cron.", "Vechiul Testament", 36, 822,"https://yahwehtora.ro/2-cronici-divre-hayamim-bet"));
    bookInfoList.add(new BookInfo("Ezra – Ezrah",15, "Ezra.", "Vechiul Testament", 10, 280,"https://yahwehtora.ro/ezra-ezrah"));
    bookInfoList.add(new BookInfo("Neemia – Nechemyah", 16,"Neem.", "Vechiul Testament", 13, 406,"https://yahwehtora.ro/neemia-nechemyah"));
    bookInfoList.add(new BookInfo("Estera – Hadasa", 17,"Est.", "Vechiul Testament", 10, 167,"https://yahwehtora.ro/estera-hadasa"));
    bookInfoList.add(new BookInfo("Iov – Yowb", 18,"Iov.", "Vechiul Testament", 42, 1070,"https://yahwehtora.ro/iov-yowb"));
    bookInfoList.add(new BookInfo("Psalmii – Tehillim",19, "Ps.", "Vechiul Testament", 150, 2461, "https://yahwehtora.ro/psalmii-tehillim"));
    bookInfoList.add(new BookInfo("Prov. lui Solomon – Mashal",20, "Prov.", "Vechiul Testament", 31, 915,"https://yahwehtora.ro/proverbele-lui-solomon-mashal"));
    bookInfoList.add(new BookInfo("Eclesiastul – Koheleth", 21,"Ecl.", "Vechiul Testament", 12, 222,"https://yahwehtora.ro/eclesiastul-koheleth"));
    bookInfoList.add(new BookInfo("Cînt. Cîntări. – Shir HaShirim",22, "Cint.Cintari.", "Vechiul Testament", 8, 117,"https://yahwehtora.ro/cintarea-cintarilor-shir-hashirim"));
    bookInfoList.add(new BookInfo("Isaia – Yeshayahu",23,"Isa.", "Vechiul Testament", 66, 1292,"https://yahwehtora.ro/isaia-yeshayahu"));
    bookInfoList.add(new BookInfo("Ieremia – Yermeyahu", 24,"Ier.", "Vechiul Testament", 52, 1364,"https://yahwehtora.ro/ieremia-yermeyahu"));
    bookInfoList.add(new BookInfo("Plîngerile lui Ieremia – Ekah",25, "PlangIer.", "Vechiul Testament", 5, 154, "https://yahwehtora.ro/plingerile-lui-ieremia-ekah"));
    bookInfoList.add(new BookInfo("Ezechiel – Yechezkel", 26,"Ezec.", "Vechiul Testament", 48, 1273,"https://yahwehtora.ro/ezechiel-yechezkel"));
    bookInfoList.add(new BookInfo("Daniel – Danieyl", 27,"Dan.", "Vechiul Testament", 12, 357,"https://yahwehtora.ro/daniel-danieyl"));
    bookInfoList.add(new BookInfo("Osea – Hoshea", 28,"Osea.", "Vechiul Testament", 14, 197,"https://yahwehtora.ro/osea-hoshea"));
    bookInfoList.add(new BookInfo("Ioel – Yah”El", 29,"Ioel.", "Vechiul Testament", 3, 73,"https://yahwehtora.ro/ioel-yahel"));
    bookInfoList.add(new BookInfo("Amos – Ahmos", 30,"Amos.", "Vechiul Testament", 9, 146,"https://yahwehtora.ro/amos-ahmos"));
    bookInfoList.add(new BookInfo("Obadia – Obadyah", 31,"Obad.", "Vechiul Testament", 1, 21,"https://yahwehtora.ro/obadia-obadyah"));
    bookInfoList.add(new BookInfo("Iona – Yownah", 32,"Iona.", "Vechiul Testament", 4, 48,"https://yahwehtora.ro/iona-yownah"));
    bookInfoList.add(new BookInfo("Mica – Micha", 33,"Mica.", "Vechiul Testament", 7, 105,"https://yahwehtora.ro/mica-micha"));
    bookInfoList.add(new BookInfo("Naum – Nachum", 34,"Naum.", "Vechiul Testament", 3, 47,"https://yahwehtora.ro/naum-nachum"));
    bookInfoList.add(new BookInfo("Habacuc – Chabakkuwk", 35,"Habac.", "Vechiul Testament", 3, 56,"https://yahwehtora.ro/habacuc-chabakkuwk"));
    bookInfoList.add(new BookInfo("Țefania – Tsephanyah", 36,"Tef.", "Vechiul Testament", 3, 53, "https://yahwehtora.ro/tefania-tsephanyah"));
    bookInfoList.add(new BookInfo("Hagai – Chaggay", 37,"Hagai.", "Vechiul Testament", 2, 38, "https://yahwehtora.ro/hagai-chaggay"));
    bookInfoList.add(new BookInfo("Zaharia – Zecharyah", 38,"Zaha.", "Vechiul Testament", 14, 211,"https://yahwehtora.ro/zaharia-zecharyah"));
    bookInfoList.add(new BookInfo("Maleahi – Malachi", 39,"Maleahi.", "Vechiul Testament", 4, 55,"https://yahwehtora.ro/maleahi-malachi"));
    bookInfoList.add(new BookInfo("Cartea lui Tobit – Tobiyah", 40,"Tobit.", "Vechiul Testament", 14, 247,"https://yahwehtora.ro/cartea-lui-tobit-tobiyah"));
    bookInfoList.add(new BookInfo("Cartea Iuditei", 41,"Iudita.", "Vechiul Testament", 16, 339,"https://yahwehtora.ro/cartea-iuditei"));
    bookInfoList.add(new BookInfo("Cartea lui Baruh", 42,"Baruh.", "Vechiul Testament", 5, 141, "https://yahwehtora.ro/cartea-lui-baruh"));
    bookInfoList.add(new BookInfo("Epistola lui Ieremia", 43,"EpisIerem.", "Vechiul Testament", 1, 72,"https://yahwehtora.ro/epistola-lui-ieremia"));
    bookInfoList.add(new BookInfo("Cântarea celor trei tineri", 44,"CantCelor3.", "Vechiul Testament", 1, 67, "https://yahwehtora.ro/cantarea-celor-trei-tineri"));
    bookInfoList.add(new BookInfo("Cartea a treia a lui Ezra", 45,"3Ezra.", "Vechiul Testament", 9, 468,"https://yahwehtora.ro/cartea-a-treia-a-lui-ezra"));
    bookInfoList.add(new BookInfo("Cartea înțelep. lui Solomon", 46,"IntelpSolomon.", "Vechiul Testament", 19, 435,"https://yahwehtora.ro/cartea-intelepciunii-lui-solomon"));
    bookInfoList.add(new BookInfo("Istoria Susanei", 47,"IstSusanei.", "Vechiul Testament", 1, 64,"https://yahwehtora.ro/istoria-susanei"));
    bookInfoList.add(new BookInfo("Istoria omorârii balaurului", 48,"IstOmorBal.", "Vechiul Testament", 1, 50, "https://yahwehtora.ro/istoria-omorarii-balaurului-si-a-sfaramarii-lui-bel"));
    bookInfoList.add(new BookInfo("1 Macabei", 49,"1Macab.", "Vechiul Testament", 16, 910,"https://yahwehtora.ro/1-macabei"));
    bookInfoList.add(new BookInfo("2 Macabei", 50,"2Macab.", "Vechiul Testament", 15, 558,"https://yahwehtora.ro/2-macabei"));
    bookInfoList.add(new BookInfo("3 Macabei", 51,"3Macab.", "Vechiul Testament", 7, 206,"https://yahwehtora.ro/3-macabei"));
    bookInfoList.add(new BookInfo("Rugăciunea regelui Manase", 52,"Manase.", "Vechiul Testament", 1, 15,"https://yahwehtora.ro/rugaciunea-regelui-manase"));
    bookInfoList.add(new BookInfo("Cartea lui Enoh", 53,"Enoh.", "Vechiul Testament", 107, 1061,"https://yahwehtora.ro/cartea-lui-enoh"));
    bookInfoList.add(new BookInfo("Matei – Mattityahu", 54,"Mat.", "Vechiul Testament", 28, 1071,"https://yahwehtora.ro/matei-mattityahu"));
    bookInfoList.add(new BookInfo("Marcu – Moshe", 55,"Marcu.", "Noul Testament", 16, 678,"https://yahwehtora.ro/marcu-moshe"));
    bookInfoList.add(new BookInfo("Luca – Luka", 56,"Luca.", "Noul Testament", 24, 1151,"https://yahwehtora.ro/luca-luka"));
    bookInfoList.add(new BookInfo("Ioan – Yochanan", 57,"Ioan.", "Noul Testament", 21, 879,"https://yahwehtora.ro/ioan-yochanan"));
    bookInfoList.add(new BookInfo("Fapte. Ap.- Maaseh Shlichim", 58,"Fapt.", "Noul Testament", 28, 1007,"https://yahwehtora.ro/faptele-apostolilor-maaseh-shlichim"));
    bookInfoList.add(new BookInfo("Romani – Romiyah", 59,"Rom.", "Noul Testament", 16, 433,"https://yahwehtora.ro/romani-romiyah"));
    bookInfoList.add(new BookInfo("1 Corinteni – Corintiyah Alef", 60,"1Cor.", "Noul Testament", 16, 437, "https://yahwehtora.ro/1-corinteni-corintiyah-alef"));
    bookInfoList.add(new BookInfo("2 Corinteni – Qorintiyah Bet", 61,"2Cor.", "Noul Testament", 13, 257,"https://yahwehtora.ro/2-corinteni-qorintiyah-bet"));
    bookInfoList.add(new BookInfo("Galateni – Galutyah", 62,"Gal.", "Noul Testament", 6, 149,"https://yahwehtora.ro/galateni-galutyah"));
    bookInfoList.add(new BookInfo("Efeseni – Ephsiyah", 63,"Efes.", "Noul Testament", 6, 155,"https://yahwehtora.ro/efeseni-ephsiyah"));
    bookInfoList.add(new BookInfo("Filipeni – Fylypsiyah", 64,"Filip.", "Noul Testament", 4, 104,"https://yahwehtora.ro/filipeni-fylypsiyah"));
    bookInfoList.add(new BookInfo("Coloseni – Qolesayah", 65,"Colos.", "Noul Testament", 4, 95,"https://yahwehtora.ro/coloseni-qolesayah"));
    bookInfoList.add(new BookInfo("1 Tesalon.- Tesalonikyah Alef", 66,"1Tesa.", "Noul Testament", 5, 89,"https://yahwehtora.ro/1-tesaloniceni-tesalonikyah-alef"));
    bookInfoList.add(new BookInfo("2 Tesalon. – Tesalonikyah Bet", 67,"2Tesa.", "Noul Testament", 3, 47,"https://yahwehtora.ro/2-tesaloniceni-tesalonikyah-bet"));
    bookInfoList.add(new BookInfo("1 Timotei – Timtheous Alef", 68,"1Timo.", "Noul Testament", 6, 113,"https://yahwehtora.ro/1-timotei-timtheous-alef"));
    bookInfoList.add(new BookInfo("2 Timotei – Timtheous Bet", 69,"2Timo.", "Noul Testament", 4, 83,"https://yahwehtora.ro/2-timotei-timtheous-bet"));
    bookInfoList.add(new BookInfo("Tit – Teitus", 70,"Tit.", "Noul Testament", 3, 46, "https://yahwehtora.ro/tit-teitus"));
    bookInfoList.add(new BookInfo("Filimon – Fileymon", 71,"Filimon.", "Noul Testament", 1, 25,"https://yahwehtora.ro/filimon-fileymon"));
    bookInfoList.add(new BookInfo("Evrei – Ivrim", 72,"Evr.", "Noul Testament", 13, 303,"https://yahwehtora.ro/evrei-ivrim"));
    bookInfoList.add(new BookInfo("Iacov – Yaacov", 73,"Iacov.", "Noul Testament", 5, 108,"https://yahwehtora.ro/iacov-yaacov"));
    bookInfoList.add(new BookInfo("1 Petru – Kepfa Alef", 74,"1Petr.", "Noul Testament", 5, 105,"https://yahwehtora.ro/1-petru-kepfa-alef"));
    bookInfoList.add(new BookInfo("2 Petru – Kefa Bet", 75,"2Petr.", "Noul Testament", 3, 61,"https://yahwehtora.ro/2-petru-kefa-bet"));
    bookInfoList.add(new BookInfo("1 Ioan – Yochanan Alef", 76,"1Ioan.", "Noul Testament", 5, 105,"https://yahwehtora.ro/1-ioan-yochanan-alef"));
    bookInfoList.add(new BookInfo("2 Ioan – Yochanan Bet", 77,"2Ioan.", "Noul Testament", 1, 13,"https://yahwehtora.ro/2-ioan-yochanan-bet"));
    bookInfoList.add(new BookInfo("3 Ioan – Yochanan Gimel", 78,"3Ioan.", "Noul Testament", 1, 14, "https://yahwehtora.ro/3-ioan-yochanan-gimel"));
    bookInfoList.add(new BookInfo("Iuda – Yahudah", 79,"Iuda.", "Noul Testament", 1, 25,"https://yahwehtora.ro/iuda-yahudah"));
    bookInfoList.add(new BookInfo("Apocalipsa – Gilyahna", 80,"Apoc.", "Noul Testament", 22, 404,"https://yahwehtora.ro/apocalipsa-gilyahna"));
    }

    public BookInfo getBookInfoByBookName(String bookName) throws Exception {
        return bookInfoList.stream().filter(book -> book.bookName().startsWith(bookName.trim()))
                .findFirst().orElseThrow(() -> new Exception("BookNotFound"));
    }
}
