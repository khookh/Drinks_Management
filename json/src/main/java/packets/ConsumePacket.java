package packets;

import info.Consumption;
import info.CustomDrink;

import java.sql.Date;

/**
 * Packet sent by the user when it consumes a drink.
 *
 * @author GriffinBabe
 */
public class ConsumePacket extends ActionPacket {

    private Consumption consumption;

    public ConsumePacket(String token, Consumption consumption) {
        super(token);
        this.consumption = consumption;
        super.packetName = this.getClass().getSimpleName();
    }

    public ConsumePacket(String token, Date date, CustomDrink customDrink) {
        super(token);
        this.consumption = new Consumption(customDrink, date);
        super.packetName = this.getClass().getSimpleName();
    }

    public ConsumePacket(String token, Date date, String alcoholName, float alcoholPercent, float alcoholVolume) {
        super(token);
        CustomDrink drink = new CustomDrink(alcoholName, alcoholVolume, alcoholPercent);
        this.consumption = new Consumption(drink, date);
        super.packetName = this.getClass().getSimpleName();
    }

    public Consumption getConsumption() {
        return consumption;
    }
}
