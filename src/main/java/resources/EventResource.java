package resources;

import core.User;
import db.EventDAO;
import io.dropwizard.auth.Auth;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Jens on 26-Sep-16.
 */
@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {
    private EventDAO eventDAO;

    public EventResource(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @POST
    public void createEvent(@Auth User user, @FormParam("startTime") @NotNull long startTimeMillis, @FormParam("endTime") long endTimeMillis) {

    }
}
