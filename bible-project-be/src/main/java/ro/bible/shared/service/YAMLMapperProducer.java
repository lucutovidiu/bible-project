package ro.bible.shared.service;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class YAMLMapperProducer {

    @Produces
    public YAMLMapper produceYAMLMapper() {
        return new YAMLMapper();
    }
}
