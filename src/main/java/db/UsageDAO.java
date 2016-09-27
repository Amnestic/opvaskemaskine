package db;

import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Created by Jens on 27-Sep-16.
 */
public interface UsageDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS usage (" +
            "id SERIAL," +
            "owner varchar(100)," +
            "type varchar(30)," +
            ""
            ");")
    void createUsageTable();
}
