package webapi;

import info.Consumption;
import info.CustomDrink;
import org.junit.jupiter.api.*;
import packets.*;
import serializers.ReadJSON;
import serializers.WriteJSON;
import services.AuthenticationService;
import services.CantStartServiceException;
import services.ConsumeService;
import services.DrinkService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsumeControllerTest {

    private CustomDrink drink = new CustomDrink("beer", 0.25f, 0.05f);
    private String token = null;
    private TestUserUtils utils = new TestUserUtils();

    @BeforeAll
    void init() {
        System.out.println(String.format("Initializing local server on ip: %d", WebServer.DEFAULT_PORT));
        WebServer server = new WebServer(WebServer.DEFAULT_PORT);
        server.start();
        utils.registerUser();
        token = utils.logUser();
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



    @Test
    @Order(2)
    void consumeDrink() {
        Consumption consumption = new Consumption(drink, new Date());
        ConsumePacket packet = new ConsumePacket(token, consumption);
        try {
            String responseJson = HttpUtils.sendPost("", WriteJSON.writePacket(packet));
            JSONPacket responsePacket = ReadJSON.readPacket(responseJson);
            assertTrue(responsePacket instanceof ResponseConsumePacket);
            assertTrue(((ResponseConsumePacket)responsePacket).isSuccess());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(3)
    void getConsumptions() {
        // adds some new consumptions
        consumeDrink();
        consumeDrink();

        // 3 consumptions so far
        AskConsumptionsPacket packet = new AskConsumptionsPacket(token, 3);
        try {
            String responseJson = HttpUtils.sendPost("", WriteJSON.writePacket(packet));
            JSONPacket responsePacket = ReadJSON.readPacket(responseJson);
            assertTrue(responsePacket instanceof  ResponseConsumptionsPacket);
            assertTrue(((ResponseConsumptionsPacket) responsePacket).isSuccess());
            List<Consumption> consumptionList = Arrays.asList(((ResponseConsumptionsPacket) responsePacket).getConsumptions());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @AfterAll
    void clear() {
        utils.clearUser();
    }
}
