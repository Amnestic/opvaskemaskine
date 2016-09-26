package resources;

import api.Event;
import com.codahale.metrics.annotation.Timed;
import core.User;
import db.EventDAO;
import io.dropwizard.auth.Auth;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.Date;

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
    @Timed
    @Produces(MediaType.TEXT_HTML)
    public String createEvent(@Auth User user, @FormParam("startTime") long startTimeMillis, @FormParam("endTime") long endTimeMillis) {
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTimeInMillis(startTimeMillis);
        endTime.setTimeInMillis(endTimeMillis);

        // Validations todo something already fills time slot
        if (startTimeMillis > endTimeMillis ||
                (endTime.get(Calendar.HOUR_OF_DAY) < 6 || endTime.get(Calendar.HOUR_OF_DAY) > 22) ||
                (startTime.get(Calendar.HOUR_OF_DAY) < 6 || endTime.get(Calendar.HOUR_OF_DAY) > 22)) {
            throw new WebApplicationException(400);
        }


        //eventDAO.insertEvent(new Event(startTime.getTime(), endTime.getTime(), user.getName()));
        return "hej";
    }
}
