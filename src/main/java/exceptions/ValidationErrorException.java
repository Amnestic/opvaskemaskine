package exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by Jens on 27-Sep-16.
 */
public class ValidationErrorException extends WebApplicationException {
    public ValidationErrorException() {
        super(Response.status(Response.Status.BAD_REQUEST).build());
    }
}
