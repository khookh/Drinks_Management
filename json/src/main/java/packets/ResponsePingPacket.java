package packets;

import java.util.Date;

/**
 * This packet is sent by the server when
 * an user goes to the /ping/ url path.
 *
 * @author GriffinBabe
 */
public class ResponsePingPacket extends ResponsePacket {

    private Date date;

    public ResponsePingPacket() {
        super(true, "Server date here.");
        date = new Date();
        super.packetName = this.getClass().getSimpleName();
    }

    public Date getDate() {
        return date;
    }
}
