package ro.bible.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "application-config")
public interface ApplicationConfig {
    String apiKey();
}
