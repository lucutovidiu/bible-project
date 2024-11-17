package ro.bible.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "migration")
public interface MigrationConfig {
    /**
     * Flag to determine if the migration should start.
     */
    boolean enabled();
}
