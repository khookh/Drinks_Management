package serializers;

import org.junit.jupiter.api.Test;
import packets.JSONPacket;
import packets.LoginPacket;
import packets.ResponseLoginPacket;
import packets.ResponsePacket;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link ReadJSON} JUnit test class.
 *
 * @author GriffinBabe
 */
class ReadJSONTest {

    @Test
    void unknownPacket() {
        String json = "{\"packetName\":\"UnknownPacket\"}";

        try {
            ReadJSON.readPacket(json);
        } catch (RuntimeException e) {
            return; // exception thrown, test passed
        }

        fail(); // exception not thrown
    }

    @Test
    void readLoginPacket() {
        LoginPacket packet = new LoginPacket("testUser", "testPassword");
        String json = WriteJSON.writePacket(packet);

        LoginPacket readPacket = (LoginPacket) ReadJSON.readPacket(json);

        assertNotNull(readPacket);
        assertEquals(readPacket.getUsername(), "testUser");
        assertEquals(packet.getPassword(), "testPassword");
    }

    @Test
    void readResponseLoginPacket() {
        ResponseLoginPacket packet = new ResponseLoginPacket(false, "Wrong password.", "null");
        String json = WriteJSON.writePacket(packet);

        ResponseLoginPacket readPacket = (ResponseLoginPacket) ReadJSON.readPacket(json);

        assertFalse(readPacket.isSuccess());
        assertEquals(readPacket.getMessage(), "Wrong password.");
        assertEquals(readPacket.getToken(), "null");
    }

    @Test
    void readPacketWithNullValues() {
        // checks if packet is not mixed with the generic ResponsePacket, as a null value in a java class
        // variable is not written in the JSON file.
        ResponseLoginPacket packet = new ResponseLoginPacket(false, "Wrong password.", null);
        String json = WriteJSON.writePacket(packet);

        System.out.println(json);

        assertFalse(json.contains("\"token\""));

        JSONPacket readPacket = (ResponsePacket) ReadJSON.readPacket(json);

        assertTrue(readPacket instanceof ResponseLoginPacket);
    }

}