package controllers;

import io.javalin.http.Context;
import packets.*;
import services.AuthenticationService;
import services.CantStartServiceException;

import java.sql.SQLException;

/**
 * User controller api used to login, search for users.
 *
 * @author GriffinBabe
 */
public class UserController extends Controller {

    private AuthenticationService authService;

    public UserController() {
        this.authService = new AuthenticationService();
        try {
            authService.start();
        } catch (CantStartServiceException e) {
            e.printStackTrace();
            System.exit(-1); // essential service.
        }
    }

    @Override
    public void handleRequest(Context ctx, JSONPacket packet) {
        if (packet instanceof LoginPacket) {
            handlePacket(ctx, (LoginPacket) packet);
        }
        else if (packet instanceof RegisterPacket) {
            handlePacket(ctx, (RegisterPacket) packet);
        }
    }

    /**
     * Handles a login packet, searching in the database for
     * username and password, sends back a {@link packets.ResponseLoginPacket}.
     *
     * @param ctx Javalin {@link Context}.
     * @param packet a {@link JSONPacket} object.
     */
    private void handlePacket(Context ctx, LoginPacket packet) {
        String username = packet.getUsername();
        String password = packet.getPassword();
        try {
            String token = authService.registerUser(username, password);
            returnPacket(ctx, new ResponseLoginPacket(true, "Authentication successful.", token));
        } catch (SQLException e) {
            returnPacket(ctx, new ResponseLoginPacket(false, "Server error occurred while logging user", null));
        }
    }

    /**
     * Handles a login packet, registering in the database an new
     * user, sends back a {@link packets.ResponseRegisterPacket}.
     *
     * @param ctx javalin {@link Context}.
     * @param packet a {@link JSONPacket} object.
     */
    private void handlePacket(Context ctx, RegisterPacket packet) {
        String username = packet.getUsername();
        String password = packet.getPassword();
        String usernameValidity = authService.isUsernameValid(username);
        String passwordValidity = authService.isPasswordValid(password);
        if (usernameValidity != null) {
            returnPacket(ctx, new ResponseRegisterPacket(false, usernameValidity, null));
        }
        else if (passwordValidity != null) {
            returnPacket(ctx, new ResponseRegisterPacket(false, passwordValidity, null));
        }
        else {
            try {
                if (!authService.isUsernameAvailable(username)) {
                    returnPacket(ctx, new ResponseRegisterPacket(false, "This username is already taken.", null));
                }
                else {
                    String token = authService.registerUser(username, password);
                    returnPacket(ctx, new ResponseRegisterPacket(true, "Registration successful.", token));
                }
            } catch (SQLException e) {
                 returnPacket(ctx, new ResponseRegisterPacket(false, "Server error occurred while registering user", null));
            }
        }
    }

}