package ro.bible.yahwehtora.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInfo {
    private int bookOrder;
    private String bookName;
    private int expChaptersCount;
    private int expTotalVerses;
    private String abbreviation;
    private String testament;
    private String downloadUrl;
    private String baseFolderPath;
    private String storedFileName;
}

