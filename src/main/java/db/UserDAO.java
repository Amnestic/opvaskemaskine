package db;

import core.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Jens on 26-Sep-16.
 */
@RegisterMapper(UserMapper.class)
public interface UserDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS users (" +
            "id SERIAL," +
            "username varchar(100) ," +
            "password varchar(250)," +
            "salt varchar(50)," +
            "role smallint," +
            "UNIQUE(username)" +
            ");")
    void createUsersTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS roles (" +
            "id SERIAL," +
            "role_name varchar(10)," +
            "UNIQUE(id)" +
            ");" +
            "INSERT INTO roles (id, role_name) VALUES (0, 'admin') ON CONFLICT DO NOTHING;" +
            "INSERT INTO roles (id, role_name) VALUES (1, 'default') ON CONFLICT DO NOTHING;")
    void createRoleTable();

    @SqlUpdate("INSERT INTO users (username, password, salt, role) VALUES (:username, :password, :salt, :role)")
    void insertUser(@Bind("username") String username, @Bind("password") String password, @Bind("salt") String salt, @Bind("role") int role);

    @SqlQuery("SELECT CASE WHEN COUNT(users.id) > 0 THEN 1 ELSE 0 END " +
            "FROM users WHERE username = :username AND password = :password")
    int authenticateUser(@Bind("username") String username, @Bind("password") String password);


    @SqlQuery("SELECT users.username as username, role.role_name as role FROM username JOIN roles ON roles.id = roles WHERE username = :username")
    User getUser(String username);

    @SqlQuery("SELECT salt FROM users WHERE username = :username")
    String getSaltForUser(@Bind("username") String username);

    @SqlQuery("SELECT username FROM users WHERE username = :username")
    String getRoleForUser(@Bind("username") String username);
}
