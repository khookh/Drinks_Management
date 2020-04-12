package webapi;

import data.User;
import info.Gender;
import org.junit.jupiter.api.*;
import packets.*;
import serializers.ReadJSON;
import serializers.WriteJSON;
import services.AuthenticationService;
import services.CantStartServiceException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    /** Environment variables */
    Map<String, String> variables = System.getenv();

    private static String ENV_TEST_USERNAME = "DManager_test_username";
    private static String ENV_TEST_PASSWORD  = "DManager_test_password";

    static String token;

    @BeforeAll
    static void initServer() {
        token = null;
        System.out.println(String.format("Initializing local server on ip: %d", WebServer.DEFAULT_PORT));
        WebServer server = new WebServer(WebServer.DEFAULT_PORT);
        server.start();
    }

    @Test
    @Order(1)
    void areVariablesSet() {
        String username = variables.get(ENV_TEST_USERNAME);
        String password = variables.get(ENV_TEST_PASSWORD);
        assertNotNull(username);
        assertNotNull(password);
    }

    @Test
    @Order(2)
    void registerUser() {
        String username = variables.get(ENV_TEST_USERNAME);
        String password = variables.get(ENV_TEST_PASSWORD);

        RegisterPacket packet = new RegisterPacket(username, "dummymail", password, 25, 60, Gender.MAN);
        String json = WriteJSON.writePacket(packet);
        System.out.println("Sending: "+json);

        try {
            String jsonResponse = HttpUtils.sendPost("", json);
            System.out.println("Response: "+jsonResponse);
            JSONPacket response = ReadJSON.readPacket(jsonResponse);
            assertTrue(response instanceof ResponseRegisterPacket);
            assertTrue(((ResponseRegisterPacket) response).isSuccess());
            assertNotNull(((ResponseRegisterPacket) response).getToken());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(3)
    void logUser() {
        String username = variables.get(ENV_TEST_USERNAME);
        String password = variables.get(ENV_TEST_PASSWORD);

        LoginPacket packet = new LoginPacket(username, password);
        String json = WriteJSON.writePacket(packet);
        System.out.println("Sending: "+json);

        try {
            String jsonResponse = HttpUtils.sendPost("", json);
            System.out.println("Response :"+jsonResponse);
            JSONPacket response = ReadJSON.readPacket(jsonResponse);
            assertTrue(response instanceof ResponseLoginPacket);
            assertTrue(((ResponseLoginPacket) response).isSuccess());
            assertNotNull(((ResponseLoginPacket) response).getToken());

            token = ((ResponseLoginPacket) response).getToken();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
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
            assertEquals(userData.getUsername(), variables.get(ENV_TEST_USERNAME));
            assertEquals(userData.getToken(), token);
        } catch (SQLException | CantStartServiceException e) {
            e.printStackTrace();
            fail();
        }
    }

    @AfterAll
    static void clearUser() {
        String username = System.getenv().get(ENV_TEST_USERNAME);
        AuthenticationService service = new AuthenticationService();
        try {
            service.start();
            service.deleteUser(username);
        } catch (SQLException | CantStartServiceException e) {
            e.printStackTrace();
            System.out.println("COULDN'T DELETE TEST USER FROM DATABASE.");
            fail();
        }
    }
}
