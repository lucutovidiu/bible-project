package ro.bible.importexport.util;

import lombok.Setter;
import ro.bible.shared.util.FileUtil;

import java.io.File;

public class BookPojoYamlPathUtil {
    private final String testament;
    private final String abbreviation;
    @Setter
    private String resourceFolder = FileUtil.FULLPATH_BIBLE_BASE_FOLDER_NAME;

    public BookPojoYamlPathUtil(String testament, String abbreviation) {
        this.testament = testament;
        this.abbreviation = abbreviation;
    }

    private String getAbbreviationWithoutDot() {
        return abbreviation.substring(0, abbreviation.length() - 1).toLowerCase();
    }

    private String getTestamentFolderName() {
        return testament.toLowerCase().replaceAll(" ", "-");
    }

    private String getYamlBasePathDirectory() {
        return getTestamentFolderName() + File.separator + getAbbreviationWithoutDot();
    }

    public String getYamlFileName() {
        return getAbbreviationWithoutDot() + ".yaml";
    }

    private String getBaseYamlPathFolder() {
        return resourceFolder + File.separator + "exported-yml-books";
    }

    public String getTestamentBaseFolder() {
        return getBaseYamlPathFolder() + File.separator + getTestamentFolderName();
    }

    public String getYamlFullBasePath() {
        return getBaseYamlPathFolder() + File.separator + getYamlBasePathDirectory();
    }

    public String getYamlFullBaseFile() {
        return getYamlFullBasePath() + File.separator + getYamlFileName();
    }
}
