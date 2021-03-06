package db;

import api.Usage;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsageMapper implements ResultSetMapper<Usage> {
    @Override
    public Usage map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Usage(resultSet.getString("type"), resultSet.getInt("counter"), resultSet.getString("owner"));
    }
}
