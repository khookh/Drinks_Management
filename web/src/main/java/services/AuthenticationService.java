package services;

import database.DBController;
import util.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService implements Service {

    public static Logger logger = new Logger(true);

    boolean running = false;

    static String TABLE_NAME = "users";

    static String USERNAME_FIELD = "username";

    static String HASHED_PASSWORD_FIELD = "password";

    static String TOKEN_FIELD = "token";

    static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
            USERNAME_FIELD + " VARCHAR(256) NOT NULL, " +
            HASHED_PASSWORD_FIELD + " VARCHAR(256) NOT NULL, " +
            TOKEN_FIELD + " VARCHAR(256) );";

    @Override
    public void start() throws CantStartServiceException {
        logger.log(this, "Starting service...");
        this.running = true;
        if (!this.checkTables()) {
            logger.log(this,"Tables not created in database. Creating table now.");
            createTable();
        }
        logger.log(this, "Service is ready.");
    }

    @Override
    public void stop() {
        logger.log(this, "Service stopping...");
        logger.log(this, "Service stopped!");
    }

    @Override
    public void pause() {
        logger.log(this, "[Authentication Service] Service paused!");
    }

    @Override
    public void resume() {
        logger.log(this, "[Authentication Service] Service closed!");
    }

    private boolean checkTables() {
        try {
            DBController connection = DBController.getInstance();
            ResultSet set = connection.getDatabaseTableData(TABLE_NAME);
            return (set.next()); // if next returns false then no table
        } catch (SQLException e) {
            logger.log(this, e.toString());
            e.printStackTrace();
            this.stop();
            return false;
        }
    }

    private void createTable() {
        try {
            DBController controller = DBController.getInstance();
            controller.executeStatement(CREATE_TABLE);
        } catch (SQLException e) {
            logger.log(this, e.toString());
            e.printStackTrace();
            this.stop();
        }
    }

}
