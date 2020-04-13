package webapi;

import info.User;
import org.junit.jupiter.api.*;
import services.AuthenticationService;
import services.CantStartServiceException;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    /** Environment variables */
    Map<String, String> variables = System.getenv();
    String token;

    TestUserUtils createTestUser = new TestUserUtils();

    @BeforeAll
    void initServer() {
        token = null;
        System.out.println(String.format("Initializing local server on ip: %d", WebServer.DEFAULT_PORT));
        WebServer server = new WebServer(WebServer.DEFAULT_PORT);
        server.start();
    }

    @Test
    @Order(1)
    void areVariablesSet() {
        createTestUser.checkVariables();
    }

    @Test
    @Order(2)
    void registerUser() {
        createTestUser.registerUser();
    }

    @Test
    @Order(3)
    void logUser() {
        token = createTestUser.logUser();
    }

    @Test
    @Order(4)
    void getUserData() {
        if (token == null) {
            fail(); // test is dependant from logUser test.
        }
        AuthenticationService service = new AuthenticationService();
        try {
            service.start();
            User userData = service.authenticateUser(token);
            assertEquals(userData.getToken(), token);
        } catch (SQLException | CantStartServiceException e) {
            e.printStackTrace();
            fail();
        }
    }

    @AfterAll
    void clearUser() {
        createTestUser.clearUser();
    }
}
