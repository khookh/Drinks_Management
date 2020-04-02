package database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link DBController}'s test class.
 */
class DBControllerTest {

    @Test
    void getInstance() {
        DBController controller = DBController.getInstance();
        assertNotNull(controller);
    }
}