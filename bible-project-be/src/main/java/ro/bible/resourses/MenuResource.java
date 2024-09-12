package ro.bible.resourses;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import ro.bible.resourses.dto.BibleBookInfoDto;
import ro.bible.resourses.service.MenuService;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/menu")
public class MenuResource {

    @Inject
    MenuService menuService;

    @GET
    public List<BibleBookInfoDto> getMenu() {
        Log.info("Get menu");
        return menuService.getBibleBooks();
    }
}
