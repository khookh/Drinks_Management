package webapi;

import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.post("/api/*", ctx -> {
            ctx.result("Connection worked.");
        });
    }
}
