package db;

import api.Event;
import core.Util;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Jens on 27-Sep-16.
 */
public class EventMapper implements ResultSetMapper<Event> {
    @Override
    public Event map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Event(Util.convertMillisToDate(resultSet.getTimestamp("start_time").getTime()),
                Util.convertMillisToDate(resultSet.getTimestamp("end_time").getTime()),
                resultSet.getString("owner"));
    }
}
