package packets;

/**
 * Abstract base class for JSON serialization
 * trough google's {@link com.google.gson.Gson}
 * library.
 *
 * @author GriffinBabe
 */
public abstract class JSONPacket {

    protected String packetName = null;

    public String getPacketName() {
        return packetName;
    }

}
