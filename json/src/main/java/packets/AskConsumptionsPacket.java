package packets;

/**
 * Packet sent by the user to ask the last x consumptions.
 *
 * @author GriffinBabe
 */
public class AskConsumptionsPacket extends ActionPacket {

    /** Max number of consumption to show. */
    int consumptionCount;

    public AskConsumptionsPacket(String token, int consumptionCount) {
        super(token);
        // for the moment it is hard coded
        this.consumptionCount = 10;
        super.packetName = this.getClass().getSimpleName();
    }

    public int getConsumptionCount() {
        return consumptionCount;
    }
}
