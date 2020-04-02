package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * DBController is a singleton patterned class that
 * communicates to the sql database.
 *
 * @author GriffinBabe
 */
public class DBController {

    /** Environment variable name for the user. */
    static String SYS_ENV_USER = "DManager_DB_User";

    /** Environment variable name for the password */
    static String SYS_ENV_PWD = "DManager_DB_Pwd";

    /** Environment variable name for the ip */
    static String SYS_ENV_IP = "DManager_DB_Ip";

    /** String needed by {@link Connection} to connect to the sql database. */
    static String CONNECTION_ADDRESS = "jdbc:mysql://%s?user=%s&password=%s";

    /** Singleton {@link DBController} instace. */
    private static DBController instance = null;

    /** Connection, interface to the MySQL server. */
    private Connection connection = null;

    /**
     * {@link DBController} constructor.
     * Private as we use singleton pattern.
     */
    private DBController() throws SQLException {
        Map<String, String> enVariables = System.getenv();
        String username = enVariables.get(SYS_ENV_USER);
        String password = enVariables.get(SYS_ENV_PWD);
        String ip = enVariables.get(SYS_ENV_IP);
        String connectionString = String.format(CONNECTION_ADDRESS, ip, username, password);
        connection = DriverManager.getConnection(connectionString);
    }

    /**
     * Singleton design pattern. Gets the instance of
     * {@link DBController} to access the SQL database.
     * @return
     */
    public static DBController getInstance() throws SQLException {
        if (DBController.instance == null) {
            DBController.instance = new DBController();
        }
        return DBController.instance;
    }

}
