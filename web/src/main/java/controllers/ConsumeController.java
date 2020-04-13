package controllers;

import io.javalin.http.Context;
import packets.AskConsumptionsPacket;
import packets.ConsumePacket;
import packets.JSONPacket;

public class ConsumeController extends Controller {

    @Override
    public void handleRequest(Context ctx, JSONPacket packet) {
        if (packet instanceof ConsumePacket) {
            handlePacket(ctx, (ConsumePacket) packet);
        }
        else if (packet instanceof AskConsumptionsPacket) {
            handlePacket(ctx, (AskConsumptionsPacket) packet);
        }
    }

    private void handlePacket(Context ctx, ConsumePacket packet) {

    }

    private void handlePacket(Context ctx, AskConsumptionsPacket packet) {

    }

}
