package services;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 * DBController is a singleton patterned class that
 * communicates to the sql database.
 *
 * @author GriffinBabe
 */
class DBConnection {

    /**
     * Environment variable name for the user.
     */
    static String SYS_ENV_USER = "DManager_DB_User";

    /**
     * Environment variable name for the password
     */
    static String SYS_ENV_PWD = "DManager_DB_Pwd";

    /**
     * Environment variable name for the ip
     */
    static String SYS_ENV_IP = "DManager_DB_Ip";

    /**
     * Name of the database from the mysql server. Hard coded
     */
    static String DB_NAME = "DManager";

    static int DEFAULT_PORT = 3306;

    private Properties connectionProperties = new Properties();

    /**
     * String containing all the information to connect to the database.
     */
    private String connectionString;

    /**
     * Singleton {@link DBConnection} instace.
     */
    private static DBConnection instance = null;

    /**
     * Connection, interface to the MySQL server.
     */
    private Connection connection = null;

    /**
     * {@link DBConnection} constructor.
     * Private as we use singleton pattern.
     */
    private DBConnection() throws SQLException {
        Map<String, String> enVariables = System.getenv();
        String username = enVariables.get(SYS_ENV_USER);
        String password = enVariables.get(SYS_ENV_PWD);
        String ip = enVariables.get(SYS_ENV_IP);

        connectionProperties.put("user", username);
        connectionProperties.put("password", password);

        // this.connectionString = String.format(CONNECTION_ADDRESS, ip, DEFAULT_PORT, DB_NAME, username, password);
        this.connectionString = "jdbc:mysql://"+ip+":"+DEFAULT_PORT+"/"+DB_NAME;

        // We instantiate the connection object but we close it asap.
        connection = DriverManager.getConnection(this.connectionString, connectionProperties);
        this.connection.close();
    }

    /**
     * Singleton design pattern. Gets the instance of
     * {@link DBConnection} to access the SQL database.
     *
     * @return
     */
    static DBConnection getInstance() throws SQLException {
        if (DBConnection.instance == null) {
            DBConnection.instance = new DBConnection();
        }
        return DBConnection.instance;
    }

    /**
     * Wrapper around {@link Connection#prepareStatement(String)} to keep
     * the connection management encapsulated.
     *
     * @param query the SQL query to execute
     * @return a {@link PreparedStatement}.
     * @throws SQLException if there is a problem with the connection.
     */
    PreparedStatement getPreparedStmt(String query) throws SQLException {
        if (connection.isClosed()) {
            this.connection = DriverManager.getConnection(connectionString, connectionProperties);
        }
        return connection.prepareStatement(query);
    }

    /**
     * Same as {@link #getPreparedStmt(String)}, but the {@link PreparedStatement#executeUpdate()}
     * will return the key of the last generated element.
     */
    PreparedStatement getInsertPreparedStmt(String query) throws SQLException {
        if (connection.isClosed()) {
            this.connection = DriverManager.getConnection(connectionString, connectionProperties);
        }
        return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * Wrapper around {@link Statement#executeQuery(String)} to keep
     * the connection management encapsulated.
     *
     * @param stmt prepared and parametrized statement
     * @return the {@link ResultSet}.
     * @throws SQLException if there is a problem with the connection or the query.
     */
    ResultSet executePreparedQuery(PreparedStatement stmt) throws SQLException {
        ResultSet set = stmt.executeQuery();
        return set;
    }

    /**
     * See {@link #executePreparedQuery(PreparedStatement)}. Same
     * but with a simple {@link Statement} instead of {@link PreparedStatement}.
     */
    ResultSet executeQuery(String query) throws SQLException {
        if (connection.isClosed()) {
            this.connection = DriverManager.getConnection(connectionString, connectionProperties);
        }
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    int executeUpdate(String update) throws SQLException {
        if (connection.isClosed()) {
            this.connection = DriverManager.getConnection(connectionString, connectionProperties);
        }
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(update);
    }

    int executePreparedUpdate(PreparedStatement stmt) throws SQLException {
        return stmt.executeUpdate();
    }

    /**
     * Returns a set of the metadata from the specified table.
     *
     * @param tableName, the name we want to look for
     * @throws SQLException
     */
    ResultSet getDatabaseTableData(String tableName) throws SQLException {
        if (connection.isClosed()) {
            this.connection = DriverManager.getConnection(connectionString, connectionProperties);
        }
        DatabaseMetaData meta = connection.getMetaData();
        return meta.getTables(null, null, tableName, new String[] {"TABLE"});
    }

    void close() throws SQLException {
        this.connection.close();
    }


}
