package ro.bible.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "import-export")
public interface ImportExportConfig {
    /**
     * Flag to determine if the migration should start.
     */
    boolean exportEnabled();

    boolean importEnabled();

    boolean fileMetadataEnabled();
}
