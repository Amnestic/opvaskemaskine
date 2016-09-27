package resources;

import db.UsageDAO;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Jens on 27-Sep-16.
 */

@Path("/usage")
@Produces(MediaType.APPLICATION_JSON)
public class UsageResource {
    private UsageDAO usageDAO;

    public UsageResource(UsageDAO usageDAO) {
        this.usageDAO = usageDAO;
    }
}
