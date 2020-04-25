package webapi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import packets.LoginPacket;
import serializers.WriteJSON;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * {@link WebServer}'s test class.
 * Will test some requests and checks
 * if the answers are correct or not.
 *
 * @author GriffinBabe
 */
class WebServerTest {

    /**
     * Initializes a local {@link WebServer},
     * starting listening to the {@link WebServer#DEFAULT_PORT}.
     */
    @BeforeAll
    static void initServer() {
        System.out.println(String.format("Initializing local server on ip: %d", WebServer.DEFAULT_PORT));
        WebServer server = new WebServer(WebServer.DEFAULT_PORT);
        server.start();
    }

    /**
     * Tries the connection by sending a Http GET request.
     */
    @Test
    void sendGetRequest() {
        try {
            String response = HttpUtils.sendGet();
            assertFalse(response.contains("404"));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tries the connection by sending a http POST request.
     */
    @Test
    void sendPostRequest() {
        try {
            LoginPacket packet = new LoginPacket("testUser", "testPassword");
            String response = HttpUtils.sendPost("", WriteJSON.writePacket(packet));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}