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
import java.util.Date;
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
    public void createEvent(@Auth User user, @FormParam("startTime") @NotNull Long startTime, @FormParam("endTime") @NotNull Long endTime) {
        validateEvent(startTime, endTime);
        eventDAO.insertEvent(new Event(Util.convertMillisToDate(startTime), Util.convertMillisToDate(endTime), user.getName()));
    }

    @GET
    @Path("/interval")
    public List<Event> getEventsInInterval(@QueryParam("startTime") @NotNull Long startTime, @QueryParam("endTime") @NotNull Long endTime) {
        return eventDAO.getEventsInInterval(Util.convertMillisToDate(startTime), Util.convertMillisToDate(endTime));
    }

    @DELETE
    public void deleteEvent(@Auth User user, @FormParam("startTime") @NotNull Long startTime, @FormParam("endTime") @NotNull Long endTime) {
        eventDAO.deleteEvent(user.getName(), Util.convertMillisToDate(startTime), Util.convertMillisToDate(endTime));
    }

    @PUT
    public void editEvent(@Auth User user, @FormParam("startTimeOld") @NotNull Long startTimeOld, @FormParam("endTimeOld") @NotNull Long endTimeOld,
                          @FormParam("startTimeNew") Long startTimeNew, @FormParam("endTimeNew") Long endTimeNew) {
        validateEvent(startTimeNew, endTimeNew);
        eventDAO.updateEvent(user.getName(), Util.convertMillisToDate(startTimeOld), Util.convertMillisToDate(endTimeOld),
                Util.convertMillisToDate(startTimeNew), Util.convertMillisToDate(endTimeNew));
    }

    private void validateEvent(long startTime, long endTime) throws ValidationErrorException {
        Date startDate = Util.convertMillisToDate(startTime);
        Date endDate = Util.convertMillisToDate(endTime);
        Calendar startDateCalendar = Calendar.getInstance();
        Calendar endDateCalendar = Calendar.getInstance();
        startDateCalendar.setTimeInMillis(startTime);
        endDateCalendar.setTimeInMillis(endTime);

        List<Event> overlappingEvents = eventDAO.getEventsOverlappingInterval(startDate, endDate);
        if (startDate.after(endDate) ||
                (endDateCalendar.get(Calendar.HOUR_OF_DAY) < 6 || endDateCalendar.get(Calendar.HOUR_OF_DAY) > 22) ||
                (startDateCalendar.get(Calendar.HOUR_OF_DAY) < 6 || endDateCalendar.get(Calendar.HOUR_OF_DAY) > 22) ||
                overlappingEvents.size() > 0) {
            throw new ValidationErrorException();
        }
    }
}
