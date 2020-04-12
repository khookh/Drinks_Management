package services;

import data.User;
import database.DBController;
import util.Hasher;
import util.Logger;
import util.StringGenerators;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService implements Service {

    // ----- SETTINGS ---------
    private static int usernameMinLength = 6;
    private static int usernameMaxLength = 15;

    private static int passwordMinLength = 6;
    private static int passwordMaxLength = 15;

    private static int TOKEN_LENGTH = 32;

    public static Logger logger;
    
    boolean running = false;

    public static String TABLE_NAME = "users";

    private static String USERNAME_FIELD = "username";

    private static String HASHED_PASSWORD_FIELD = "password";

    private static String TOKEN_FIELD = "token";

    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
            USERNAME_FIELD + " VARCHAR(256) NOT NULL UNIQUE, " +
            HASHED_PASSWORD_FIELD + " VARCHAR(256) NOT NULL, " +
            TOKEN_FIELD + " VARCHAR(256) UNIQUE);";

    private static String GET_USERNAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME_FIELD +"=?;";

    private static String GET_TOKEN = "SELECT * FROM " + TABLE_NAME + " WHERE " + TOKEN_FIELD +"=?;";

    private static String ADD_USER = "INSERT INTO " + TABLE_NAME + "("+USERNAME_FIELD+","+HASHED_PASSWORD_FIELD+") values(?,?);";

    private static String SET_TOKEN = "UPDATE " + TABLE_NAME + " SET " + TOKEN_FIELD + "=? WHERE " + USERNAME_FIELD + "=?;";

    private static String DELETE_USER = "DELETE FROM " + TABLE_NAME + " WHERE " + USERNAME_FIELD + "=?;";

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
            controller.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            logger.log(this, e.toString());
            e.printStackTrace();
            this.stop();
        }
    }


    /**
     * Checks in the database if {@param token} is already used.
     * (Very rare, should normally never happen).
     *
     * @param token the token to verify
     * @return true if a match was found, false otherwise.
     * @throws SQLException if there is a problem with the database
     */
    private boolean isTokenAvailable(String token) throws SQLException {
        DBController controller = DBController.getInstance();
        PreparedStatement stmt = controller.getPreparedStmt(GET_TOKEN);
        stmt.setString(1, token);
        ResultSet set = controller.executePreparedQuery(stmt);
        return (!set.next());
    }

    /**
     * Generates a token until the generated token is unique (must always be the case).
     * Once it's generated, sets the token to the username.
     *
     * @param username, the username where we want to set the token.
     * @return the generated token
     * @throws SQLException if there is a problem with the database
     */
    private String generateAndSetToken(String username) throws SQLException {
        String token;
        do {
            token = StringGenerators.generateToken(TOKEN_LENGTH);
        } while(!isTokenAvailable(token));

        DBController controller = DBController.getInstance();
        PreparedStatement stmt = controller.getPreparedStmt(SET_TOKEN);
        stmt.setString(1, token);
        stmt.setString(2, username);
        controller.executePreparedUpdate(stmt);
        return token;
    }


    /**
     * Checks if the username is available in the database.
     *
     * @param username the username we want to check
     * @return true if it is available, false otherwise.
     * @throws SQLException if there is a problem with the database
     */
    public boolean isUsernameAvailable(String username) throws SQLException {
        DBController controller = DBController.getInstance();
        PreparedStatement stmt = controller.getPreparedStmt(GET_USERNAME);
        stmt.setString(1, username);
        ResultSet set = controller.executePreparedQuery(stmt);
        boolean ret = set.next();
        controller.close();
        return !ret;
    }

    /**
     * Registers an user, this function implies that the {@link Controller}
     * already checked the validity of the password and the availability of the username.
     * @param username
     * @param password
     * @return the token of the registered user
     * @throws SQLException if there is an exception with the database
     */
    public String registerUser(String username, String password) throws SQLException {
        String hashedPassword = Hasher.hashString(password);
        DBController controller = DBController.getInstance();
        PreparedStatement stmt = controller.getPreparedStmt(ADD_USER);
        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);
        stmt.execute();
        controller.close();
        return logUser(username, password);
    }

    /**
     * Registers an user, returns a string depending on how the connection
     * was successful or not.
     * @param username
     * @param password
     * @return null if the password or the username was not valid,
     *         returns a token if the log was successful.
     * @throws SQLException if there is an exception with the database
     */
    public String logUser(String username, String password) throws SQLException {
        DBController controller = DBController.getInstance();
        PreparedStatement stmt = controller.getPreparedStmt(GET_USERNAME);
        stmt.setString(1, username);
        ResultSet set = controller.executePreparedQuery(stmt);
        if (!set.next()) {
            controller.close();
            return null;
        }
        String registeredPassword = set.getString(HASHED_PASSWORD_FIELD);
        if (Hasher.verifyString(password, registeredPassword)) {
            controller.close();
            return generateAndSetToken(username);
        }
        controller.close();
        return null;
    }

    /**
     * Here we describe the rules for choosing an username.
     * @param username, the username we want to check.
     * @return a String with a message in case of error, null if everything's good.
     */
    public String isUsernameValid(String username) {
        if (!StandardCharsets.US_ASCII.newEncoder().canEncode(username)) {
            return "Username must only contain ASCII characters. No accent nor special " +
                    "characters must be used.";
        }
        if (username.length() > usernameMaxLength || username.length() < usernameMinLength) {
            return "Username must be between 6 and 15 characters.";
        }
        return null;
    }

    /**
     * Here we describe the rules for choosing a password.
     * @param password, the password we want to check.
     * @return a String with a message in case of error, null if everything's good.
     */
    public String isPasswordValid(String password) {
        if (!StandardCharsets.US_ASCII.newEncoder().canEncode(password)) {
            return "Password must only contain ASCII characters. No accent nor special " +
                    "characters must be used.";
        }
        if (password.length() > passwordMaxLength || password.length() < passwordMinLength) {
            return "Password must be between 6 and 15 characters.";
        }
        return null;
    }

    /**
     * Authenticates an user with a token given by the client.
     * @return an {@link User object} containing user important data.
     */
    public User authenticateUser(String token) throws SQLException {
        DBController controller = DBController.getInstance();
        PreparedStatement stmt = controller.getPreparedStmt(GET_TOKEN);
        stmt.setString(1, token);
        ResultSet set = controller.executePreparedQuery(stmt);
        if (!set.next()) {
            controller.close();
            return null;
        }
        String username = set.getString(USERNAME_FIELD);
        controller.close();
        return new User(username, token);
     }

    /**
     * Deletes an user from the database. Used after each unit test.
     * @param username the username of the user to delete.
     * @throws SQLException if there is an error with the database.
     */
     public void deleteUser(String username) throws SQLException {
        DBController controller = DBController.getInstance();
        PreparedStatement stmt = controller.getPreparedStmt(DELETE_USER);
        stmt.setString(1, username);
        int retState = controller.executePreparedUpdate(stmt);
        controller.close();
     }

}
