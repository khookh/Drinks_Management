package packets;

/**
 * Login packet. Base class sent by the app
 * to ask a login token.
 */
public class LoginPacket extends ActionPacket {

    private String username;
    private String password;

    public LoginPacket(String username, String password) {
        super();
        super.packetName = this.getClass().getSimpleName();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
