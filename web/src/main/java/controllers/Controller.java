package controllers;

import io.javalin.http.Context;
import packets.JSONPacket;

public abstract class Controller {

    abstract void handleRequest(Context ctx, JSONPacket packet);

}
