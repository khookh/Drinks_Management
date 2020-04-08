package packets;

/**
 * This is the reponse of the {@link RegisterPacket}. Token
 * is set to null if the registration failed. The reason why
 * the registration would fail or succeed is written in the message.
 *
 * @author GriffinBabe
 */
public class ResponseRegisterPacket extends ResponsePacket {

    private String token = null;

    public ResponseRegisterPacket(boolean success, String message, String token) {
        super(success, message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
