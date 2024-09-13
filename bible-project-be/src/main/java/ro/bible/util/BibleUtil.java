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
        bookInfoList.add(new BookInfo("Genesa – Bereshith", "Fac.", "Vechiul Testament",50, 1533));
        bookInfoList.add(new BookInfo("Exod – Shemoth", "Exod.", "Vechiul Testament",40,1213));
        bookInfoList.add(new BookInfo("Levitic – Wayiqra", "Lev.", "Vechiul Testament",27,859));
        bookInfoList.add(new BookInfo("Numeri – Bamidbar", "Num.", "Vechiul Testament",36,1288));
        bookInfoList.add(new BookInfo("Deuteronom – Devarim", "Deut.", "Vechiul Testament",34,959));
        bookInfoList.add(new BookInfo("Judecători – Shoftim", "Judec.", "Vechiul Testament",21,618));
        bookInfoList.add(new BookInfo("1 Samuel – Shmuel Alef", "1Sam.", "Vechiul Testament",31,810));
        bookInfoList.add(new BookInfo("2 Samuel - Shmuel Bet", "2Sam.", "Vechiul Testament",24,695));
        bookInfoList.add(new BookInfo("1 Împărați – Melechim Alef", "1Reg.", "Vechiul Testament",22,816));
        bookInfoList.add(new BookInfo("2 Împărați – Melechim Bet", "2Reg.", "Vechiul Testament",25,719));
        bookInfoList.add(new BookInfo("1 Cron. – Divre HaYamim Alef", "1Cron.", "Vechiul Testament",29,942));
        bookInfoList.add(new BookInfo("2 Cron. – Divre HaYamim Bet", "2Cron.", "Vechiul Testament",36,822));
        bookInfoList.add(new BookInfo("Ezra – Ezrah", "Ezra.", "Vechiul Testament",10,280));
        bookInfoList.add(new BookInfo("Neemia – Nechemyah", "Neem.", "Vechiul Testament",13,406));
        bookInfoList.add(new BookInfo("Estera – Hadasa", "Est.", "Vechiul Testament",10,167));
        bookInfoList.add(new BookInfo("Iov – Yowb", "Iov.", "Vechiul Testament",42,1070));
        bookInfoList.add(new BookInfo("Psalmii - Tehillim", "Psa.", "Vechiul Testament",150,2461));
        bookInfoList.add(new BookInfo("Prov. lui Solomon – Mashal", "Prov.", "Vechiul Testament",31,915));
        bookInfoList.add(new BookInfo("Eclesiastul – Koheleth", "Ecl.", "Vechiul Testament",12,222));
        bookInfoList.add(new BookInfo("Cînt. Cîntări. – Shir HaShirim", "Cint.Cintari.", "Vechiul Testament",8,117));
        bookInfoList.add(new BookInfo("Isaia – Yeshayahu", "Isa.", "Vechiul Testament",66,1292));
        bookInfoList.add(new BookInfo("Ieremia – Yermeyahu", "Ier.", "Vechiul Testament",52,1364));
        bookInfoList.add(new BookInfo("Ezechiel – Yechezkel", "Ezec.", "Vechiul Testament",48,1273));
        bookInfoList.add(new BookInfo("Daniel – Danieyl", "Dan.", "Vechiul Testament",12,357));
        bookInfoList.add(new BookInfo("Osea – Hoshea", "Osea.", "Vechiul Testament",14,197));
        bookInfoList.add(new BookInfo("Ioel – Yah”El", "Ioel.", "Vechiul Testament",3,73));
        bookInfoList.add(new BookInfo("Amos – Ahmos", "Amos.", "Vechiul Testament",9,146));
        bookInfoList.add(new BookInfo("Obadia – Obadyah", "Obad.", "Vechiul Testament",1,21));
        bookInfoList.add(new BookInfo("Iona – Yownah", "Iona.", "Vechiul Testament",4,48));
        bookInfoList.add(new BookInfo("Mica – Micha", "Mica.", "Vechiul Testament",7,105));
        bookInfoList.add(new BookInfo("Naum – Nachum", "Naum.", "Vechiul Testament",3,47));
        bookInfoList.add(new BookInfo("Habacuc – Chabakkuwk", "Habac.", "Vechiul Testament",3,56));
        bookInfoList.add(new BookInfo("Zaharia – Zecharyah", "Zaha.", "Vechiul Testament",14,211));
        bookInfoList.add(new BookInfo("Maleahi – Malachi", "Maleahi.", "Vechiul Testament",4,55));
        bookInfoList.add(new BookInfo("Matei – Mattityahu", "Mat.", "Vechiul Testament",28,1071));
        bookInfoList.add(new BookInfo("Marcu – Moshe", "Marcu.", "Noul Testament",16,678));
        bookInfoList.add(new BookInfo("Luca – Luka", "Luca.", "Noul Testament",24,1151));
        bookInfoList.add(new BookInfo("Ioan – Yochanan", "Ioan.", "Noul Testament",21,879));
        bookInfoList.add(new BookInfo("Fapte. Ap.- Maaseh Shlichim", "Fapt.", "Noul Testament",28,1007));
        bookInfoList.add(new BookInfo("Romani – Romiyah", "Rom.", "Noul Testament",16,433));
        bookInfoList.add(new BookInfo("1 Corinteni - Corintiyah Alef", "1Cor.", "Noul Testament",16,437));
        bookInfoList.add(new BookInfo("2 Corinteni – Qorintiyah Bet", "2Cor.", "Noul Testament",13,257));
        bookInfoList.add(new BookInfo("Galateni – Galutyah", "Gal.", "Noul Testament",6,149));
        bookInfoList.add(new BookInfo("Efeseni – Ephsiyah", "Efes.", "Noul Testament",6,155));
        bookInfoList.add(new BookInfo("Filipeni – Fylypsiyah", "Filip.", "Noul Testament",4,104));
        bookInfoList.add(new BookInfo("Coloseni – Qolesayah", "Colos.", "Noul Testament",4,95));
        bookInfoList.add(new BookInfo("1 Tesalon.- Tesalonikyah Alef", "1Tesa.", "Noul Testament",5,89));
        bookInfoList.add(new BookInfo("2 Tesalon. – Tesalonikyah Bet", "2Tesa.", "Noul Testament",3,47));
        bookInfoList.add(new BookInfo("1 Timotei – Timtheous Alef", "1Timo.", "Noul Testament",6,113));
        bookInfoList.add(new BookInfo("2 Timotei – Timtheous Bet", "2Timo.", "Noul Testament",4,83));
        bookInfoList.add(new BookInfo("Tit - Teitus", "Tit.", "Noul Testament",3,46));
        bookInfoList.add(new BookInfo("Filimon – Fileymon", "Filimon.", "Noul Testament",1,25));
        bookInfoList.add(new BookInfo("Evrei – Ivrim", "Evr.", "Noul Testament",13,303));
        bookInfoList.add(new BookInfo("Iacov – Yaacov", "Iacov.", "Noul Testament",5,108));
        bookInfoList.add(new BookInfo("1 Petru – Kepfa Alef", "1Petr.", "Noul Testament",5,105));
        bookInfoList.add(new BookInfo("2 Petru – Kefa Bet", "2Petr.", "Noul Testament",3,61));
        bookInfoList.add(new BookInfo("1 Ioan – Yochanan Alef", "1Ioan.", "Noul Testament",5,105));
        bookInfoList.add(new BookInfo("2 Ioan – Yochanan Bet", "2Ioan.", "Noul Testament",1,13));
        bookInfoList.add(new BookInfo("3 Ioan - Yochanan Gimel", "3Ioan.", "Noul Testament",1,14));
        bookInfoList.add(new BookInfo("Iuda – Yahudah", "Iuda.", "Noul Testament",1,25));
        bookInfoList.add(new BookInfo("Apocalipsa – Gilyahna", "Apoc.", "Noul Testament",22,404));
        bookInfoList.add(new BookInfo("Cartea lui Tobit – Tobiyah", "Tobit.", "Vechiul Testament",14,247));
        bookInfoList.add(new BookInfo("Cartea Iuditei", "Iudita.", "Vechiul Testament",16,339));
        bookInfoList.add(new BookInfo("Epistola lui Ieremia", "EpisIerem.", "Vechiul Testament",1,72));
        bookInfoList.add(new BookInfo("Cartea a treia a lui Ezra", "3Ezra.", "Vechiul Testament",9,468));
        bookInfoList.add(new BookInfo("Cartea înțelep. lui Solomon", "IntelpSolomon.", "Vechiul Testament",19,435));
        bookInfoList.add(new BookInfo("1 Macabei", "1Macab.", "Vechiul Testament",16,910));
        bookInfoList.add(new BookInfo("2 Macabei", "2Macab.", "Vechiul Testament",15,558));
        bookInfoList.add(new BookInfo("3 Macabei", "3Macab.", "Vechiul Testament",7,206));
        bookInfoList.add(new BookInfo("Rugăciunea regelui Manase", "Manase.", "Vechiul Testament",1,15));
        bookInfoList.add(new BookInfo("Cartea lui Enoh", "Enoh.", "Vechiul Testament",107,1061));
        bookInfoList.add(new BookInfo("Istoria Susanei", "IstSusanei.", "Vechiul Testament",1,64));
    }

    public BookInfo getBookInfoByBookName(String bookName) throws Exception {
        return bookInfoList.stream().filter(book -> book.bookName().startsWith(bookName.trim()))
                .findFirst().orElseThrow(() -> new Exception("BookNotFound"));
    }
}
