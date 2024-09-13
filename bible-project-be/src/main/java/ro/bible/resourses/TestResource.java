package ro.bible.resourses;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import ro.bible.maintanance.service.BookMaintenanceService;
import ro.bible.maintanance.service.BookReportingService;

@Path("test")
public class TestResource {

    @Inject
    BookMaintenanceService bookMaintenanceService;
    @Inject
    BookReportingService bookReportingService;

    @GET
    public String test() {
        bookMaintenanceService.patchAllBooks();
        return "test";
    }
}
