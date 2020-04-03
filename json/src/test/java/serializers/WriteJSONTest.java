package serializers;

import org.junit.jupiter.api.Test;
import packets.JSONPacket;
import packets.LoginPacket;
import packets.ResponseLoginPacket;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link WriteJSON} JUnit test class.
 *
 * @author GriffinBabe
 */
class WriteJSONTest {

    @Test
    void loginPacketTest() {
        // don't try this combination on our database it doesn't exists ;)
        LoginPacket packet = new LoginPacket("testUser", "testPassword");
        String json = WriteJSON.writePacket(packet);

        assertNotNull(json);
        assertTrue(json.contains("\"username\":\"testUser\""));
        assertTrue(json.contains("\"password\":\"testPassword\""));
    }

    @Test
    void responsePacketTest() {
        ResponseLoginPacket packet = new ResponseLoginPacket(false, "Wrong password", "null");
        String json = WriteJSON.writePacket(packet);

        assertNotNull(json);
        assertTrue(json.contains("\"success\":false"));
        assertTrue(json.contains("\"message\":\"Wrong password\""));
        assertTrue(json.contains("\"token\":\"null\""));
    }

}