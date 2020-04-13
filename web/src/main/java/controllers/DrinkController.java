package controllers;

import io.javalin.http.Context;
import packets.JSONPacket;
import services.AuthenticationService;
import services.CantStartServiceException;
import services.DrinkService;

/**
 * Controller that manages all the requests regarding drinks.
 *
 * @author GriffinBabe
 */
public class DrinkController extends Controller {

    AuthenticationService authService = new AuthenticationService();
    DrinkService drinkService = new DrinkService();

    public DrinkController() {
        try {
            authService.start();
            drinkService.start();
        }
        catch (CantStartServiceException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void handleRequest(Context ctx, JSONPacket packet) {
    }
}
