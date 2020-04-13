package services;


import info.User;
import util.DateFormatter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * This services takes care of the consumptions
 * of a custom drink. The management of custom
 * drinks etc is in the {@link DrinkService}.
 *
 * @author GriffinBabe
 */
public class ConsumeService extends Service {

    private static String TABLE_NAME = "consumptions";

    private static String DRINK_ID_FIELD = "drink_id";

    private static String USER_ID_FIELD = "user_id";

    private static String TIME_FIELD = "time";

    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
            DRINK_ID_FIELD + " INT UNSIGNED NOT NULL, " +
            USER_ID_FIELD + " INT UNSIGNED NOT NULL, " +
            TIME_FIELD + " DATETIME NOT NULL, " +
            "FOREIGN KEY (" + DRINK_ID_FIELD + ") REFERENCES " + DrinkService.TABLE_NAME + "(id)," +
            "FOREIGN KEY (" + USER_ID_FIELD+ ") REFERENCES " + AuthenticationService.TABLE_NAME + "(id) );";

    private static String ADD_CONSUMPTION = "INSERT INTO " + TABLE_NAME +
            " (" + DRINK_ID_FIELD + "," + USER_ID_FIELD + "," + TIME_FIELD +") " +
            "VALUES(?,?,?);";

    @Override
    public void start() throws CantStartServiceException {
        startCreateTables(TABLE_NAME, CREATE_TABLE);
    }

    @Override
    public void stop() {
        SERVICE_LOGGER.log(this, "Service stopping...");
        SERVICE_LOGGER.log(this, "Service stopped!");
    }

    @Override
    public void pause() {
        SERVICE_LOGGER.log(this, "Service paused!");
    }

    @Override
    public void resume() {
        SERVICE_LOGGER.log(this, "Service closed!");
    }

    public int addConsumption(int drinkId, int userId) throws SQLException {
        try {
            String dateString = DateFormatter.toString(new Date());
            DBConnection connection = DBConnection.getInstance();
            PreparedStatement stmt = connection.getInsertPreparedStmt(ADD_CONSUMPTION);
            stmt.setInt(1, drinkId);
            stmt.setInt(2, userId);
            stmt.setString(3, dateString);
            int entryId = connection.executePreparedUpdate(stmt);
            connection.close();
            return entryId;
        } catch (ParseException e) {
            // this error should never happen here
            e.printStackTrace();
            System.exit(-1);
        }
        return -1;
    }
}
