package webapi;

import controllers.Controller;
import controllers.UserController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import packets.ConsumePacket;
import packets.JSONPacket;
import packets.ResponsePingPacket;
import serializers.ReadJSON;
import serializers.WriteJSON;
import services.AuthenticationService;
import services.ConsumeService;
import services.DrinkService;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Web server class contains all the {@link Javalin} object.
 * Responsible of handling all the POST requestes used by
 * the DManager clients, forming the first point where the
 * API calls come.
 *
 * @author GriffinBabe
 */
public class WebServer {

    // www default port
    static int DEFAULT_PORT = 80;

    /** Server {@link Logger}, for the moment it is shared across all the server software. */
    private static Logger serverLogger = new Logger(true);

    static {
        AuthenticationService.logger = WebServer.serverLogger;
        Controller.logger = WebServer.serverLogger;
        ConsumeService.logger = WebServer.serverLogger;
    }

    private int port = DEFAULT_PORT;

    private List<Controller> controllerList = new ArrayList<>();

    WebServer() {
        this(DEFAULT_PORT);
    }

    WebServer(int port) {
        this.port = port;
    }

    /**
     * Starts the server by initializing
     * the {@link Controller} list and the
     * {@link Context} handlers.
     */
    void start() {
        this.initControllers();
        this.initServer();
    }

    /**
     * Initializes the server, initializing all the
     * handles of the Javalin framework.
     */
    private void initServer() {

        Javalin server = Javalin.create().start(DEFAULT_PORT);

        // all the requests managed by the controllers
        server.post("/api/*", ctx -> {
            String json = ctx.body();
            JSONPacket packet = ReadJSON.readPacket(json);
            controllerList.forEach(controller -> { controller.handleRequest(ctx, packet);});
        });

        // for connection testing
        server.get("/", ctx -> {
            ctx.result("Hello world");
        });

        // for connection pinging
        server.get("/ping/", ctx -> {
            ResponsePingPacket rpp = new ResponsePingPacket();
            String json = WriteJSON.writePacket(rpp);
            ctx.result(json);
        });

        server.exception(Exception.class, (e, ctx) -> {
           e.printStackTrace();
        });
    }

    /**
     * Initializes the {@link Controller} list of the server.
     */
    private void initControllers() {
        this.controllerList.add(new UserController());
    }

    public static void main(String[] args) {
        WebServer webServer = new WebServer();
        webServer.start();
    }
}
