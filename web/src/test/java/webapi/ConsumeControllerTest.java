package webapi;

import controllers.ConsumeController;
import info.Consumption;
import info.CustomDrink;
import org.junit.jupiter.api.*;
import packets.ConsumePacket;
import services.AuthenticationService;
import services.CantStartServiceException;
import services.ConsumeService;
import services.DrinkService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsumeControllerTest {

    private static CustomDrink drink = new CustomDrink("beer", 0.25f, 0.05f);

    private static String token = null;

    @BeforeAll
    static void init() {
        System.out.println(String.format("Initializing local server on ip: %d", WebServer.DEFAULT_PORT));
        WebServer server = new WebServer(WebServer.DEFAULT_PORT);
        server.start();
    }

    @Test
    @Order(1)
    void checkInitTables() {
        // initialize services and their tables
        AuthenticationService authService = new AuthenticationService();
        DrinkService drinkService = new DrinkService();
        ConsumeService consumeService = new ConsumeService();

        try {
            authService.start();
            drinkService.start();
            consumeService.start();
        } catch (CantStartServiceException e) {
            e.printStackTrace();
            fail();
        }
    }

    void consumeDrink() {
        Consumption consumption = new Consumption(drink, new Date());
        // ConsumePacket packet = new ConsumePacket(consumption)
    }

    @AfterAll
    static void clear() {

    }
}
