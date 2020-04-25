package serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import packets.JSONPacket;

/**
 * Writes a JSON file from a {@link JSONPacket} class.
 * This classes uses a {@link GsonBuilder} using a custom serializer
 * {@link PolymorphicSerializer}.
 * https://stackoverflow.com/questions/19588020/gson-serialize-a-list-of-polymorphic-objects
 *
 * @author GriffinBabe
 */
public class WriteJSON {

    public static String writePacket(JSONPacket packet) {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(JSONPacket.class, new PolymorphicSerializer());
        Gson gson = gb.create();
        return gson.toJson(packet, JSONPacket.class);
    }

}
