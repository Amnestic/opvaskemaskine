package resources;

import api.Event;
import com.codahale.metrics.annotation.Timed;
import core.User;
import core.Util;
import db.EventDAO;
import exceptions.ValidationErrorException;
import io.dropwizard.auth.Auth;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.List;

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
    public void createEvent(@Auth User user, @FormParam("startTime") @NotNull long startTimeMillis, @FormParam("endTime") @NotNull long endTimeMillis) {
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTimeInMillis(startTimeMillis);
        endTime.setTimeInMillis(endTimeMillis);

        // Validations
        List<Event> overlappingEvents = eventDAO.getEventsOverlappingInterval(startTime.getTime(), endTime.getTime());
        if (startTimeMillis > endTimeMillis ||
                (endTime.get(Calendar.HOUR_OF_DAY) < 6 || endTime.get(Calendar.HOUR_OF_DAY) > 22) ||
                (startTime.get(Calendar.HOUR_OF_DAY) < 6 || endTime.get(Calendar.HOUR_OF_DAY) > 22) ||
                overlappingEvents.size() > 0) {
            throw new ValidationErrorException();
        }

        eventDAO.insertEvent(new Event(startTime.getTime(), endTime.getTime(), user.getName()));
    }

    @GET
    @Path("/interval")
    public List<Event> getEventsInInterval(@QueryParam("startDate") @NotNull @Min(1) Long startDate, @QueryParam("endDate") @NotNull @Min(1) Long endDate) {
        return eventDAO.getEventsInInterval(Util.convertMillisToDate(startDate), Util.convertMillisToDate(endDate));
    }
}
