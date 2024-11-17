package ro.bible.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookPojo {
    private long bookId;
    private String name;
    private String abbreviation;
    private String testament;
    private Integer expChaptersCount;
    private Integer expTotalVerses;
    private String downloadedLink;
    private Boolean requiresUpdate;
    private Boolean inProgress;
    private int bookOrder;

    public String chapterKey() {
        return name.equalsIgnoreCase("Psalmii – Tehillim") ? "Psalmul" : "Capitolul";
    }
}
