package ro.bible.api.resource;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import ro.bible.api.model.request.BibleVerseEditRequestDto;
import ro.bible.api.model.response.VerseUpdateResponse;
import ro.bible.api.service.ApiKeyHeaderValidator;
import ro.bible.persistence.service.VerseService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/bible")
public class BibleResource {

    @Inject
    VerseService verseService;
    @Inject
    ApiKeyHeaderValidator apiKeyHeaderValidator;

    @PATCH
    @Path("/verse")
    public VerseUpdateResponse patchVerse(@HeaderParam("Api-Key") String apiKey,
                                          @RequestBody BibleVerseEditRequestDto requestDto) {
        apiKeyHeaderValidator.validateApiKey(apiKey);
        Log.infof("Patching Verse: %s", requestDto);
        boolean result = verseService.patchBibleVerse(requestDto);
        verseService.updateSearchVector(requestDto.getBookId());
        return VerseUpdateResponse.builder()
                .result(result)
                .build();
    }
}
