package db;

import api.Event;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;
import java.util.List;

/**
 * Created by Jens on 26-Sep-16.
 */
@RegisterMapper(EventMapper.class)
public interface EventDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS events (" +
            "id SERIAL," +
            "start_time TIMESTAMP NOT NULL," +
            "end_time TIMESTAMP NOT NULL," +
            "owner VARCHAR(100) NOT NULL," +
            "PRIMARY KEY(id)" +
            ");")
    void createEventTable();

    @SqlUpdate("INSERT INTO events (start_time, end_time, owner) VALUES (:event.startTime, :event.endTime, :event.owner)")
    void insertEvent(@BindBean("event") Event event);

    @SqlQuery("SELECT * FROM events WHERE start_time < :endTime AND end_time > :startTime")
    List<Event> getEventsOverlappingInterval(@Bind("startTime") Date startTime, @Bind("endTime") Date endTime);

    @SqlQuery("SELECT * FROM events WHERE start_time >= :startTime AND :endTime <= end_time")
    List<Event> getEventsInInterval(@Bind("startTime") Date startTime, @Bind("endTime") Date endTime);
}
