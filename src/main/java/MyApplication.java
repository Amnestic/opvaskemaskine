import auth.MyAuthenticator;
import core.User;
import db.EventDAO;
import db.UserDAO;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import resources.EventResource;
import resources.UserResource;

/**
 * Created by Jens on 22-Sep-16.
 */
public class MyApplication extends Application<MyConfiguration> {

    public static void main(String[] args) throws Exception {
        new MyApplication().run(args);
    }

    public void run(MyConfiguration config, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "postgresql");
        final EventDAO eventDAO = jdbi.onDemand(EventDAO.class);
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);

        // Sets up tables
        userDAO.createRoleTable();
        userDAO.createUsersTable();
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new MyAuthenticator(userDAO))
                .buildAuthFilter()));
        environment.jersey().register(new EventResource(eventDAO));
        environment.jersey().register(new UserResource(userDAO));
    }
}
