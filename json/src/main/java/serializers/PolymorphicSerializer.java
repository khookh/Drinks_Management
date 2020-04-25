package serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import packets.JSONPacket;

import java.lang.reflect.Type;

/**
 * Custom {@link com.google.gson.Gson} serializer for polymorphic
 * json serialization. Found here:
 * https://stackoverflow.com/questions/19588020/gson-serialize-a-list-of-polymorphic-objects
 *
 * This custom {@link JsonSerializer} uses the classes registered in {@link PolymorphicDeserializer#classMap}.
 *
 * @author GriffinBabe
 */
public class PolymorphicSerializer implements JsonSerializer<JSONPacket> {


    /**
     * Custom serializer that implements Polymorphism. Reads the
     * jsonElement class packet name and searches it in the packet
     * map before attempting to serialize jsonPacket.
     *
     * @param jsonPacket the object to serialize.
     * @param type
     * @param jsonSerializationContext
     * @return the serialized {@link JSONPacket}.
     */
    @Override
    public JsonElement serialize(JSONPacket jsonPacket, Type type, JsonSerializationContext jsonSerializationContext) {
        String classType = jsonPacket.getPacketName();
        Class c = PolymorphicDeserializer.classMap.get(classType);
        if (c == null) {
            throw new RuntimeException("Couldn't find class:" + classType + ".");
        }
        return jsonSerializationContext.serialize(jsonPacket, c);
    }
}
