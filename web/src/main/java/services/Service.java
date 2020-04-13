package services;

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

    /**
     * Usually called when starting a new service. This will initialize all the new tables
     * @param tableName the table to check on the SQL Database
     * @param createTable the table creation instruction for the SQL Database
     * @throws CantStartServiceException if there is a problem with the initialization of the tables.
     */
    void startCreateTables(String tableName, String createTable) throws CantStartServiceException {
        SERVICE_LOGGER.log(this, "Starting service...");
        this.running = true;
        try {
            if (!this.checkTables(tableName)) {
                SERVICE_LOGGER.log(this,"Tables not created in database. Creating table now.");
                this.createTable(createTable);
            }
            SERVICE_LOGGER.log(this, "Service is ready.");
        } catch (SQLException e) {
            this.running = false;
            throw new CantStartServiceException("Can't start service, SQL exception occurred", this);
        }
    }

    /**
     * Checks if the given table name exists or not in the database.
     * @param tableName the table to check.
     * @return true if it exists, false otherwise
     * @throws SQLException if there is a problem with the Database.
     */
    boolean checkTables(String tableName) throws SQLException {
        DBConnection connection = DBConnection.getInstance();
        ResultSet set = connection.getDatabaseTableData(tableName);
        return (set.next()); // if next returns false then no table
    }

    /**
     * Creates a new table into the database.
     * @param createTableSQL the instruction to launch
     * @throws SQLException if there is a problem with the Database.
     */
    void createTable(String createTableSQL) throws SQLException {
        DBConnection controller = DBConnection.getInstance();
        controller.executeUpdate(createTableSQL);
}





}
