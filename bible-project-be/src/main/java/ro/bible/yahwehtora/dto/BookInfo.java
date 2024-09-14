package ro.bible.yahwehtora.dto;

public record BookInfo(String bookName, int bookOrder, String abbreviation, String testament, int chaptersCount, int totalVerses, String downloadUrl) {

}
