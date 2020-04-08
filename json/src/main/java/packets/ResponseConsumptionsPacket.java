package packets;

import info.Consumption;

/**
 * Response of {@link AskConsumptionsPacket}. Gives out all
 * the information about the recent user consumptions.
 *
 * @author GriffinBabe
 */
public class ResponseConsumptionsPacket extends ResponsePacket {

    private Consumption[] consumptions;

    public ResponseConsumptionsPacket(boolean success, String message, Consumption[] consumptions) {
        super(success, message);
        this.consumptions = consumptions;
        super.packetName = this.getClass().getSimpleName();
    }

    public Consumption[] getConsumptions() {
        return consumptions;
    }
}
