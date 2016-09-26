package db;

import core.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jens on 26-Sep-16.
 */
public class UserMapper implements ResultSetMapper<User> {
    @Override
    public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new User(resultSet.getString("username"), resultSet.getString("role"));
    }
}
