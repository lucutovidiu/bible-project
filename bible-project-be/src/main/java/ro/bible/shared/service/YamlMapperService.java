package ro.bible.shared.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class YamlMapperService {
    @Inject
    YAMLMapper yamlMapper;

    public Optional<String> objectToString(Object object) {
        try {
            Log.info("Converting object to yaml");
            return Optional.of(yamlMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            Log.error("Failed to convert object to yaml", e);
            return Optional.empty();
        }
    }

    public <T> Optional<T> yamlToObject(String input, Class<T> clazz) {
        try {
            Log.info("Converting yaml to object");
            return Optional.of(yamlMapper.readValue(input, clazz));
        } catch (JsonProcessingException e) {
            Log.error("Failed to convert yaml to object", e);
            return Optional.empty();
        }
    }
}
