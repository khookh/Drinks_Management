package serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import packets.JSONPacket;

/**
 * Reads a JSON file from a JSON serialized {@link JSONPacket} class.
 * This classes uses a {@link GsonBuilder} using a custom deserializer
 * {@link PolymorphicDeserializer}.
 * https://stackoverflow.com/questions/19588020/gson-serialize-a-list-of-polymorphic-objects
 *
 * @author GriffinBabe
 */
public class ReadJSON {

    public static JSONPacket readPacket(String json) {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(JSONPacket.class, new PolymorphicDeserializer());
        Gson gson = gb.create();
        return gson.fromJson(json, JSONPacket.class);
    }

}
