package packets;

/**
 * This packet, not to mix with {@link ResponseConsumptionsPacket}
 * will notify the application that the {@link ConsumePacket} request
 * has been proceeded by the server.
 *
 * @author GriffinBabe
 */
public class ResponseConsumedPacket extends ResponsePacket {

    public ResponseConsumedPacket(boolean success, String message) {
        super(success, message);
        super.packetName = this.getClass().getSimpleName();
    }
}
