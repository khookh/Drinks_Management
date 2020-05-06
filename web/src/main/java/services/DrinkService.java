package services;

import info.CustomDrink;
import info.User;
import util.DateFormatter;

import javax.xml.transform.Result;
import java.text.ParseException;
import java.util.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DrinkService extends Service {

    public static String TABLE_NAME = "drinks";

    private static String USER_ID_FIELD = "user_id";

    private static String NAME_FIELD = "name";

    private static String VOLUME_FIELD = "volume";

    private static String PERCENT_FIELD = "percentage";

    private static String DATE_FIELD = "date";

    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
            USER_ID_FIELD + " INT UNSIGNED NOT NULL, " +
            NAME_FIELD + " varchar(256) NOT NULL, " +
            VOLUME_FIELD + " FLOAT NOT NULL, " +
            PERCENT_FIELD + " FLOAT NOT NULL, " +
            DATE_FIELD + " DATE NOT NULL, " +
            "FOREIGN KEY (" + USER_ID_FIELD + ") REFERENCES " + AuthenticationService.TABLE_NAME + "(id) ON DELETE CASCADE); ";

    private static String GET_DRINK_ID = "SELECT id FROM " + TABLE_NAME +
            " WHERE " + NAME_FIELD +"=? AND " + USER_ID_FIELD + "=?;";

    private static String ADD_DRINK = "INSERT INTO " + TABLE_NAME +
            " (" + USER_ID_FIELD + "," + NAME_FIELD + "," + VOLUME_FIELD +
            "," + PERCENT_FIELD + "," + DATE_FIELD +") VALUES(?,?,?,?,?);";

    private static String GET_DRINK_INFO = "SELECT * FROM " + TABLE_NAME +
            " WHERE id=?";

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

    /**
     * Gets the id of a drink from the given data.
     * @param drink drink data
     * @param user user data
     * @return -1 if no drink has been found. Returns
     *          the drink id if otherwise.
     */
    public int getDrinkId(CustomDrink drink, User user) throws SQLException {
        DBConnection controller = DBConnection.getInstance();
        PreparedStatement stmt = controller.getPreparedStmt(GET_DRINK_ID);
        stmt.setString(1, drink.getName());
        stmt.setInt(2, user.getId());
        ResultSet set = controller.executePreparedQuery(stmt);
        if (!set.next()) {
            controller.close();
            return -1;
        }
        controller.close();
        return set.getInt("id");
    }

    public int addDrink(CustomDrink drink, User user) throws SQLException {
        try {
            String dateString = DateFormatter.toString(new Date());
            DBConnection connection = DBConnection.getInstance();
            PreparedStatement stmt = connection.getInsertPreparedStmt(ADD_DRINK);
            stmt.setInt(1, user.getId());
            stmt.setString(2, drink.getName());
            stmt.setFloat(3, drink.getVolume());
            stmt.setFloat(4, drink.getAlcoholPercent());
            stmt.setString(5, dateString);
            ResultSet set = connection.executePreparedInsert(stmt);
            if (!set.next()) return -1;
            int entryId = set.getInt(1);
            connection.close();
            return entryId;
        } catch (ParseException e) {
            // this error should never occur
            e.printStackTrace();
            System.exit(-1);
        }
        return -1;
    }

    public CustomDrink getDrinkInfo(int drinkId) throws SQLException {
        try {
            DBConnection connection = DBConnection.getInstance();
            PreparedStatement stmt = connection.getPreparedStmt(GET_DRINK_INFO);
            stmt.setInt(1, drinkId);
            ResultSet set = connection.executePreparedQuery(stmt);
            if (!set.next()) return null;
            String name = set.getString(NAME_FIELD);
            float volume = set.getFloat(VOLUME_FIELD);
            float perc = set.getFloat(PERCENT_FIELD);
            CustomDrink drink = new CustomDrink(name, volume, perc);
            connection.close();
            return drink;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

}
