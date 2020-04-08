package packets;

/**
 * Base class for all responses. From the server to the client.
 *
 * @author GriffinBabe
 */
public abstract class ResponsePacket extends JSONPacket {

    private boolean success;
    private String message;

    ResponsePacket(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

}
