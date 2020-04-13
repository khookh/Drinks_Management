package controllers;

import info.Consumption;
import info.User;
import io.javalin.http.Context;
import packets.*;
import services.AuthenticationService;
import services.CantStartServiceException;
import services.ConsumeService;
import services.DrinkService;

import java.sql.SQLException;

public class ConsumeController extends Controller {

    AuthenticationService authService  = new AuthenticationService();
    ConsumeService consumeService = new ConsumeService();
    DrinkService drinkService = new DrinkService();

    public ConsumeController() {
        try {
            authService.start();
            drinkService.start();
            consumeService.start(); // attention! consumeService sql table is dependant of drink service, so it must
            // be initialized after!
        }
        catch (CantStartServiceException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

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
        Consumption cons = packet.getConsumption();
        try {
            User user = authService.authenticateUser(packet.getToken());
            if (user == null) {
                returnPacket(ctx, new ResponseWrongTokenPacket(false, "Wrong token"));
                return;
            }
            int drinkId = drinkService.getDrinkId(cons.getDrink(), user);
            if (drinkId == -1) {
                // TODO: Think about this, maybe a new drink should be created instead.
                drinkId = drinkService.addDrink(cons.getDrink(), user);
            }
            consumeService.addConsumption(drinkId, user.getId());
            returnPacket(ctx, new ResponseConsumePacket(true, "Consumption added successfully"));
        } catch (SQLException e) {
            e.printStackTrace();
            returnPacket(ctx, new ResponseConsumePacket(false,"Server error occurred while logging user"));
        }
    }

    private void handlePacket(Context ctx, AskConsumptionsPacket packet) {
        // TODO handle packet
        returnPacket(ctx, new ResponseConsumePacket(false, "Functionality not implemented yet."));
    }

}
