package services;


import info.Consumption;
import info.CustomDrink;
import info.User;
import util.DateFormatter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

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
            "FOREIGN KEY (" + DRINK_ID_FIELD + ") REFERENCES " + DrinkService.TABLE_NAME + "(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (" + USER_ID_FIELD+ ") REFERENCES " + AuthenticationService.TABLE_NAME + "(id) ON DELETE CASCADE);";

    private static String ADD_CONSUMPTION = "INSERT INTO " + TABLE_NAME +
            " (" + DRINK_ID_FIELD + "," + USER_ID_FIELD + "," + TIME_FIELD +") " +
            "VALUES(?,?,?);";

    private static String GET_CONSUMPTIONS = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ID_FIELD + "=? ORDER BY "
            + TIME_FIELD + " LIMIT=?;";

    private static String GET_DRINK_IDS = "SELECT DISTINCT " + DRINK_ID_FIELD + " FROM " + TABLE_NAME + " WHERE id=?;";

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

    public List<Consumption> getConsumptions(User user, int consumptionCount, DrinkService drinkService) throws SQLException {
        try {
            List<Consumption> consumptionList = new ArrayList<>();
            DBConnection connection = DBConnection.getInstance();
            PreparedStatement stmt = connection.getPreparedStmt(GET_CONSUMPTIONS);
            stmt.setInt(1, user.getId());
            stmt.setInt(2, consumptionCount);
            ResultSet set = connection.executePreparedQuery(stmt);

            Map<Integer, CustomDrink> drinkInfo = new HashMap<Integer, CustomDrink>();
            while (set.next()) {
                int drinkId = set.getInt(DRINK_ID_FIELD);
                Date date = DateFormatter.fromString(set.getString(TIME_FIELD));
                CustomDrink drink = drinkInfo.get(drinkId);
                if (drink == null) {
                    drink = drinkService.getDrinkInfo(drinkId);
                    if (drink == null) return null;
                    drinkInfo.put(drinkId, drink);
                }
                consumptionList.add(new Consumption(drink, date));
            }
            connection.close();
            return consumptionList;
        } catch (ParseException e) {
            throw new SQLException("Couldn't parse date");
        }
    }

    /**
     * Returns all the unique drink ids consumed at least
     * once by one user.
     * @return
     */
    public int[] getUserDrinkIds(User user) throws SQLException {
        DBConnection connection = DBConnection.getInstance();
        PreparedStatement stmt = connection.getPreparedStmt(GET_DRINK_IDS);
        stmt.setInt(1, user.getId());
        ResultSet set = connection.executePreparedQuery(stmt);

        // TODO get ResultSet size.
        while (set.next()) {

        }
    }


}
