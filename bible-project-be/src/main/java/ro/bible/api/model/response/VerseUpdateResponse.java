package ro.bible.api.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VerseUpdateResponse {
    private boolean result;
}
