package controllers;

import io.javalin.http.Context;
import packets.JSONPacket;
import serializers.WriteJSON;
import util.Logger;

/**
 * Controller base class. The function {@link #handleRequest(Context, JSONPacket)}
 * is for the moment the only one supposed to be accessed from outside. It will take
 * care of packets. Note that the base class must be carefully designed so each packet
 * is only handled by one controller.
 */
public abstract class Controller {

    public static Logger logger;

    /**
     * Handle request is abstract, only the controllers.
     * This method is used for overloading.
     *
     * @param ctx javalin {@link Context}.
     * @param packet the {@link JSONPacket} to handle.
     */
    public abstract void handleRequest(Context ctx, JSONPacket packet);

    void returnPacket(Context ctx, JSONPacket packet) {
        String json = WriteJSON.writePacket(packet);
        ctx.result(json);
    }
}
