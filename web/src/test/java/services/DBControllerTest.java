package services;

import org.junit.jupiter.api.Test;
import services.DBConnection;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * {@link DBConnection}'s Junit test class.
 *
 * @author GriffinBabe
 */
class DBControllerTest {

    /**
     * Tries if the user has entered all the environment variables
     */
    @Test
    void areVariablesSet() {
        Map<String, String> variables = System.getenv();

        String username = variables.get(DBConnection.SYS_ENV_USER);
        String password = variables.get(DBConnection.SYS_ENV_PWD);
        String ip = variables.get(DBConnection.SYS_ENV_IP);

        assertNotNull(username, "Environment variable not set.");
        assertNotNull(password, "Environment variable not set.");
        assertNotNull(ip, "Environment variable not set.");
    }

    /**
     * Tries to connect with the environment variables given by the user.
     */
    @Test
    void getInstance() {
        try {
            DBConnection controller = DBConnection.getInstance();
            assertNotNull(controller);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }

    }

}