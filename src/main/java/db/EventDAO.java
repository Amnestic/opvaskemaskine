package db;

import api.Event;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Created by Jens on 26-Sep-16.
 */
public interface EventDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS events (" +
            "id SERIAL," +
            "start_time DATE," +
            "end_time DATE," +
            "owner VARCHAR(100)" +
            ");")
    void createEventTable();

    @SqlUpdate("INSERT INTO events (start_time, end_time, owner) VALUES (:startTime, :endTime, :owner)")
    void insertEvent(@BindBean("event") Event event);
}
