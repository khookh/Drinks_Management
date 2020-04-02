package webapi;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * {@link WebServer}'s test class.
 * Will test some requests and checks
 * if the answers are correct or not.
 */
class WebServerTest {

    /** Ip for local testing */
    static String localHost = "127.0.0.1";

    /** Port for local testing */
    static int testPort = 80;

    WebServer server = null;

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

     * @throws UnsupportedEncodingException if the json encoding is incorrect.
     * @throws ClientProtocolException if there is a problem with the encoding of the client address.
     * @throws IOException if there is a IO Exception.
     */
    void sendPost(String path, String data) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(localHost+"/"+path);
        StringEntity entity = new StringEntity(data);
        request.addHeader("content-type", "application/x-www-from-urlencoded");
        request.setEntity(entity);

        HttpResponse response = client.execute(request);
    }

    void sendGet(String path) {
    }

    @BeforeAll
    void initServer() {
        System.out.println(String.format("Initializing local server on ip: %d", testPort));
        server = new WebServer(testPort);
    }

    @Test
    void sendGetRequest() {

    }
}