package ro.bible.api.resource;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import ro.bible.api.model.BibleBookInfoDto;
import ro.bible.api.service.MenuService;

import java.util.Comparator;
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
        return menuService.getBibleBooks().stream()
                .sorted(Comparator.comparing(BibleBookInfoDto::getBookOrder))
                .toList();
    }
}
