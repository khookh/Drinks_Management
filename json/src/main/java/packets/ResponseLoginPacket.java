package packets;

/**
 * Response from the server to the user about if
 * the connection succeeded or failed. In the
 * case it succeeded {@link #token} will not be null.
 *
 * @author GriffinBabe
 */
public class ResponseLoginPacket extends ResponsePacket {

    private String token = null;

    public ResponseLoginPacket(boolean success, String message, String token) {
        super(success, message);
        this.token = token;
        super.packetName = this.getClass().getSimpleName();
    }

    public String getToken() {
        return this.token;
    }

}
