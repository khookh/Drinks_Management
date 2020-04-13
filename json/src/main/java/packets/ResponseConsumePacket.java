package packets;

/**
 * This packet, not to mix with {@link ResponseConsumptionsPacket}
 * will notify the application that the {@link ConsumePacket} request
 * has been proceeded by the server.
 *
 * @author GriffinBabe
 */
public class ResponseConsumePacket extends ResponsePacket {

    public ResponseConsumePacket(boolean success, String message) {
        super(success, message);
        super.packetName = this.getClass().getSimpleName();
    }
}
