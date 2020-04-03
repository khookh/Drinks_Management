package packets;

public class ResponseLoginPacket extends ResponsePacket {

    private String token = null;

    public ResponseLoginPacket(boolean success, String message, String token) {
        super(success, message);
        super.packetName = this.getClass().getSimpleName();
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

}
