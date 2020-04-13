package packets;

/**
 * This can be response of any {@link ActionPacket}.
 * Sent by the server in case of a wrong token has been given.
 *
 * @author GriffinBabe
 */
public class ResponseWrongTokenPacket extends ResponsePacket {

    public ResponseWrongTokenPacket(boolean success, String message) {
        super(success, message);
        super.packetName = this.getClass().getSimpleName();
    }
}
