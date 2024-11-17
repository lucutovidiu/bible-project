package ro.bible.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

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

    public String getFilePath() {
        return baseFolderPath + File.separator + storedFileName;
    }
}

