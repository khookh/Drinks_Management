package webapi;

import controllers.UserController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import packets.JSONPacket;
import packets.LoginPacket;
import serializers.ReadJSON;
import services.AuthenticationService;
import services.Service;

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

    private Javalin server = null;

    private int port = DEFAULT_PORT;

    List<Service> serviceList = new ArrayList<>();

    UserController userController = new UserController();

    public WebServer() {
        this(DEFAULT_PORT);
    }

    public WebServer(int port) {
        this.port = port;
        initServer();
    }

    /**
     * Initializes the server, initializing all the
     * handles of the Javalin framework.
     */
    private void initServer() {
        initServices();

        this.server = Javalin.create().start(DEFAULT_PORT);

        this.server.post("/api/*", ctx -> {

        });

        this.server.get("/", ctx -> {
            ctx.result("Hello world");
        });

        this.server.exception(Exception.class, (e, ctx) -> {
           e.printStackTrace();
        });
    }

    private void initServices() {
        this.serviceList.add(new AuthenticationService());
    }

    private void handleApiRequest(Context ctx) {
        String body = ctx.body();
        JSONPacket packet = ReadJSON.readPacket(body);
    }

}
