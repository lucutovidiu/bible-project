package ro.bible.resourses;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ro.bible.db.pojo.VersePojo;
import ro.bible.resourses.service.BibleFinderService;

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

        return bibleFinderService.getChapterVerses(chapterNumer, bookId);
    }

    @GET
    @Path("/text/{verseText}")
    public List<VersePojo> findPlacesInTheBibleByVerseText(@PathParam("verseText") String verseText) {
        Log.info("Get findPlacesInTheBibleByVerseText");

        return bibleFinderService.findPlacesInTheBibleByVerseText(verseText);
    }
}
