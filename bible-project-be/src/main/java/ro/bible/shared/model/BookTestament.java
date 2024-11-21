package ro.bible.shared.model;

public enum BookTestament {
    NEW_TESTAMENT("Noul Testament"),
    OLD_TESTAMENT("Vechiul Testament"),
    APOCRYPHA("Apocrifa");

    private final String bookTestament;

    BookTestament(String bookTestament) {
        this.bookTestament = bookTestament;
    }

    public String getBookTestament() {
        return bookTestament;
    }
}
