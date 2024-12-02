package ro.bible.api.resource;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ro.bible.persistence.model.VersePojo;
import ro.bible.api.service.BibleFinderService;

import java.util.Comparator;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/bible-finder")
public class BibleFinderResource {

    @Inject
    BibleFinderService bibleFinderService;

    @GET
    @Path("/chapter-number/{chapterNumer}/book-id/{bookId}")
    public List<VersePojo> findChapterNumberByBook(@PathParam("chapterNumer") Integer chapterNumer,
                                                   @PathParam("bookId") long bookId) {
        Log.info("Get findChapterNumberByBook");

        return bibleFinderService.getChapterVerses(chapterNumer, bookId).stream()
                .sorted(Comparator.comparing(VersePojo::getVerseNumber))
                .toList();
    }

    @GET
    @Path("/text/{verseText}")
    public List<VersePojo> findPlacesInTheBibleByVerseText(@PathParam("verseText") String verseText) {
        Log.info("Get findPlacesInTheBibleByVerseText");

        return bibleFinderService.findPlacesInTheBibleByVerseText(verseText);
    }
}
