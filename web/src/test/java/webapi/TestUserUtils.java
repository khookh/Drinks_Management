package webapi;

import info.Gender;
import info.User;
import packets.*;
import serializers.ReadJSON;
import serializers.WriteJSON;
import services.AuthenticationService;
import services.CantStartServiceException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of functions to register, login and
 * destroy an user. A javalin server must be created
 * and initialized before using those functions.
 *
 * Those methods are utilities but they also test with assertions.
 */
public class TestUserUtils {

    private Map<String, String> variables = System.getenv();

    static String ENV_TEST_USERNAME = "DManager_test_username";
    static String ENV_TEST_PASSWORD  = "DManager_test_password";


    void checkVariables() {
        String username = variables.get(ENV_TEST_USERNAME);
        String password = variables.get(ENV_TEST_PASSWORD);
        assertNotNull(username);
        assertNotNull(password);
    }


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

    String logUser() {
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

            return ((ResponseLoginPacket) response).getToken();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        return null;
    }

    void clearUser() {
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
