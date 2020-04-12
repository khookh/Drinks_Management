package services;

import util.Logger;

/**
 * This services takes care of the consumptions
 * of a custom drink. The management of custom
 * drinks etc is in the {@link DrinkService}.
 *
 * @author GriffinBabe
 */
public class ConsumeService implements Service {

    public static Logger logger;

    private static String TABLE_NAME = "consumptions";

    private static String DRINK_ID_FIELD = "drink_id";

    private static String USER_ID_FIELD = "user_id";

    private static String TIME_FIELD = "time";

    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
            DRINK_ID_FIELD + " INT NOT NULL, " +
            USER_ID_FIELD + " INT NOT NULL, " +
            TIME_FIELD + " DATE NOT NULL, " +
            "FOREIGN KEY (" + DRINK_ID_FIELD + ") REFERENCES " + DrinkService.TABLE_NAME + "(id)," +
            "FOREIGN KEY (" + USER_ID_FIELD+ ") REFERENCES " + AuthenticationService.TABLE_NAME + "(id);";



    @Override
    public void start() throws CantStartServiceException {

    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
