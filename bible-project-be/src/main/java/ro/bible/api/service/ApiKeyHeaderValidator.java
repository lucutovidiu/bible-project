package ro.bible.api.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ro.bible.api.exception.CustomException;
import ro.bible.config.ApplicationConfig;

@ApplicationScoped
public class ApiKeyHeaderValidator {

    @Inject
    ApplicationConfig applicationConfig;

    public void validateApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty() || !apiKey.equals(applicationConfig.apiKey())) {
            Log.errorf("Invalid apiKey: %s", apiKey);
            throw new CustomException("UNAUTHORIZED", 401);
        }
    }
}
