package serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import packets.*;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * Custom {@link com.google.gson.Gson} deserializer for polymorphic
 * classes deserialization. Found here:
 * https://stackoverflow.com/questions/19588020/gson-serialize-a-list-of-polymorphic-objects
 *
 * @author GriffinBabe
 */
public class PolymorphicDeserializer implements JsonDeserializer<JSONPacket> {

    /**
     * Map containing all the names of the classes and the classes references attaches
     * to it.
     *
     * Every new packet must be added in this classMap in a static block.
     */
    static Map<String, Class> classMap = new TreeMap<String, Class>();

    static {
        classMap.put(JSONPacket.class.getSimpleName(), JSONPacket.class);
        classMap.put(ActionPacket.class.getSimpleName(), ActionPacket.class);
        classMap.put(ResponsePacket.class.getSimpleName(), ResponsePacket.class);
        classMap.put(LoginPacket.class.getSimpleName(), LoginPacket.class);
        classMap.put(ResponseLoginPacket.class.getSimpleName(), ResponseLoginPacket.class);
        classMap.put(AskConsumptionsPacket.class.getSimpleName(), AskConsumptionsPacket.class);
        classMap.put(ConsumePacket.class.getSimpleName(), ConsumePacket.class);
        classMap.put(RegisterPacket.class.getSimpleName(), RegisterPacket.class);
        classMap.put(ResponseConsumePacket.class.getSimpleName(), ResponseConsumePacket.class);
        classMap.put(ResponseConsumptionsPacket.class.getSimpleName(), ResponseConsumptionsPacket.class);
        classMap.put(ResponseRegisterPacket.class.getSimpleName(), ResponseRegisterPacket.class);
        classMap.put(ResponsePingPacket.class.getSimpleName(), ResponsePingPacket.class);
        classMap.put(ResponseWrongTokenPacket.class.getSimpleName(), ResponseWrongTokenPacket.class);
    }

    /**
     * Custom deserializer that implements Polymorphism. Reads the
     * jsonElement class packet name and searches it in the packet
     * map before attempting to deserializing it.
     *
     * @param jsonElement the json to deserialize
     * @param type
     * @param jsonDeserializationContext
     * @return the deserialized {@link JSONPacket}.
     * @throws JsonParseException if no class is found in the {@link #classMap} with the found packetName.
     */
    @Override
    public JSONPacket deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String classType = jsonElement.getAsJsonObject().get("packetName").getAsString();
        Class c = classMap.get(classType);
        if (c == null) {
            throw new RuntimeException("Couldn't find class:" + classType + ".");
        }
        return jsonDeserializationContext.deserialize(jsonElement, c);
    }
}
