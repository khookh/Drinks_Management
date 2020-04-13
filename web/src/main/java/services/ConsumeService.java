package services;

import util.Logger;

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
            DRINK_ID_FIELD + " INT NOT NULL, " +
            USER_ID_FIELD + " INT NOT NULL, " +
            TIME_FIELD + " DATE NOT NULL, " +
            "FOREIGN KEY (" + DRINK_ID_FIELD + ") REFERENCES " + DrinkService.TABLE_NAME + "(id)," +
            "FOREIGN KEY (" + USER_ID_FIELD+ ") REFERENCES " + AuthenticationService.TABLE_NAME + "(id);";

    @Override
    public void start() throws CantStartServiceException {
        SERVICE_LOGGER.log(this, "Starting service...");
        this.running = true;
        if (!this.checkTables(TABLE_NAME)) {
            SERVICE_LOGGER.log(this,"Tables not created in database. Creating table now.");
            this.createTable(CREATE_TABLE);
        }
        SERVICE_LOGGER.log(this, "Service is ready.");
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
}
