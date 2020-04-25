package packets;

/**
 * Login packet. Base class sent by the app
 * to ask a login token.
 *
 * @author GriffinBabe
 */
public class LoginPacket extends JSONPacket {

    private String username;
    private String password;

    public LoginPacket(String username, String password) {
        this.username = username;
        this.password = password;
        super.packetName = this.getClass().getSimpleName();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
