package services;

import com.mysql.jdbc.log.Log;
import database.DBController;
import util.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service interface used by the {@link webapi.WebServer} class and package.
 *
 * Services can be run stopped
 */
public abstract class Service {

    public static Logger SERVICE_LOGGER;

    boolean running = false;

    /**
     * Starts a service, initializing all the data
     * and necessary modules.
     */
    abstract public void start() throws CantStartServiceException;

    /**
     * Stops a service, closing all the data and the
     * necessary modules.
     */
    abstract public void stop();

    /**
     * Pauses a service, data and modules are not
     * closed but service cannot perform actions.
     */
    abstract public void pause();

    /**
     * Resumes a paused service. Enabling once
     * again the usage of that service.
     */
    abstract public void resume();

    boolean checkTables(String tableName) {
        try {
            DBController connection = DBController.getInstance();
            ResultSet set = connection.getDatabaseTableData(tableName);
            return (set.next()); // if next returns false then no table
        } catch (SQLException e) {
            SERVICE_LOGGER.log(this, e.toString());
            e.printStackTrace();
            this.stop();
            return false;
        }
    }

    void createTable(String createTableSQL) {
        try {
            DBController controller = DBController.getInstance();
            controller.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            SERVICE_LOGGER.log(this, e.toString());
            e.printStackTrace();
            this.stop();
        }
    }





}
