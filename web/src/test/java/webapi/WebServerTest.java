package webapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import packets.LoginPacket;
import serializers.WriteJSON;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * {@link WebServer}'s test class.
 * Will test some requests and checks
 * if the answers are correct or not.
 *
 * @author GriffinBabe
 */
class WebServerTest {

    /** Ip for local GET testing */
    static String URL = "http://localhost/";

    /** IP for local POST testing */
    static String API_URL = "http://localhost/api/";

    /** Static WebServer instance used for all tests. */
    static WebServer server = null;

    // TODO: Use JSON with Google's GSON.
    /**
     * Sends a Http POST request to the specified
     * {@param path} with the following {@link String}
     * as data.
     *
     * @param path url where to send the post request, note that
     *             the server address will automatically be put before.
     *             Don't put a '/' at the beginning of the path.
     * @param data data sent to the server in the POST request.
     * @return the {@link HttpResponse} to string call.
     * @throws UnsupportedEncodingException if the json encoding is incorrect.
     * @throws ClientProtocolException if there is a problem with the encoding of the client address.
     * @throws IOException if there is a IO Exception.
     *
     */
    String sendPost(String path, String data) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(API_URL +path);
        StringEntity entity = new StringEntity(data);
        request.addHeader("content-type", "application/x-www-from-urlencoded");
        request.setEntity(entity);

        HttpResponse response = client.execute(request);
        return response.toString();
    }

    /**
     * Returns {@link #sendGet(String)} with an empty string.
     */
    String sendGet() throws IOException {
        return sendGet("");
    }

    /**
     * Sends a simple HTTP request to the specified path.
     *
     * @param path url where to send the get request, note
     *             that the server address will automatically
     *             be put before. Don't put a '/' at the
     *             beginning of the path.
     * @return the {@link HttpResponse} to string call.
     * @throws ClientProtocolException if there is a problem with the encoding of the client address.
     * @throws IOException if there is a IO Exception.
     */
    String sendGet(String path) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(URL +path);

        HttpResponse response = client.execute(request);
        return response.toString();
    }

    /**
     * Initializes a local {@link WebServer},
     * starting listening to the {@link WebServer#DEFAULT_PORT}.
     */
    @BeforeAll
    static void initServer() {
        System.out.println(String.format("Initializing local server on ip: %d", WebServer.DEFAULT_PORT));
        server = new WebServer(WebServer.DEFAULT_PORT);
    }

    /**
     * Tries the connection by sending a Http GET request.
     */
    @Test
    void sendGetRequest() {
        try {
            String response = sendGet();
            assertFalse(response.contains("404"));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tries the connection by sending a http POST request.
     */
    @Test
    void sendPostRequest() {
        try {
            LoginPacket packet = new LoginPacket("testUser", "testPassword");
            String response = sendPost("", WriteJSON.writePacket(packet));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}