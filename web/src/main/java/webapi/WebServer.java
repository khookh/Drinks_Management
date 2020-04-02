package webapi;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * Web server class contains all the {@link Javalin} object.
 * Responsible of handling all the POST requestes used by
 * the DManager clients, forming the first point where the
 * API calls come.
 */
public class WebServer {

    static int DEFAULT_PORT = 80;

    private Javalin server = null;

    private int port;

    public WebServer() {
        this(DEFAULT_PORT);
    }

    public WebServer(int port) {
        this.port = port;
        initServer();
    }

    private void initServer() {
        this.server = Javalin.create().start(DEFAULT_PORT);
        this.server.post("/api/*", ctx -> {
            handleApiRequest(ctx);
        });

        this.server.get("/", ctx -> {
            ctx.result("Hello world");
        });

        this.server.exception(Exception.class, (e, ctx) -> {
           e.printStackTrace();
        });
    }

    private void handleApiRequest(Context ctx) {

    }

}
