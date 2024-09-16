package ro.bible.yahwehtora.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bible.util.FileUtil;

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

    public String getWritableFullPath() {
        return FileUtil.RESOURCE_FOLDER + File.separator + baseFolderPath + File.separator + storedFileName;
    }

    public String getWritableBasePath() {
        return FileUtil.RESOURCE_FOLDER + File.separator + baseFolderPath;
    }

    public String getReadablePath() {
        return baseFolderPath + File.separator + storedFileName;
    }
}

