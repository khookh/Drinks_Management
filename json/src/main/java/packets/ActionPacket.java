package packets;

/**
 * Actions packets are actions or requests done by a logged
 * user to the server. This mother class is used as it contains
 * the authentication token that is needed for such  actions.
 *
 * @author GriffinBabe
 */
public abstract class ActionPacket extends JSONPacket {

    private String token;

    public ActionPacket(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
