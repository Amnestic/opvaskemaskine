package resources;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import core.RoleHelper;
import db.UserDAO;
import org.apache.commons.lang3.RandomStringUtils;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jens on 26-Sep-16.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @POST
    public void createUser(@FormParam("username") @NotNull String username, @FormParam("password") String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String salt = RandomStringUtils.randomAlphanumeric(50);
        String hashedAndSaltedPassword = HexBin.encode(messageDigest.digest((password + salt).getBytes()));

        // Validations
        if (username.isEmpty() || password.isEmpty()) {
            throw new WebApplicationException(400);
        }

        try {
            userDAO.insertUser(username, hashedAndSaltedPassword, salt, RoleHelper.ROLE_DEFAULT);
        } catch (Exception e) {
            throw new WebApplicationException(400);
        }
    }

    // TODO update password and that kind of shit
}
