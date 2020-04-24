package network;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
//todo: implements packets, http functions and testing



public class HttpManager {

    static String URL = "";
    static String API_URL = "";

    public HttpManager(String URL, String API_URL){
        setApiUrl(API_URL);
        setURL(URL);
    }

    public String sendPost(String path, String data) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        //timeout si la connexion prend trop de temps
        int timeout = 5; // seconds
        HttpParams httpParams = client.getParams();
        HttpConnectionParams.setConnectionTimeout(
                httpParams, timeout * 1000); // http.connection.timeout
        HttpConnectionParams.setSoTimeout(
                httpParams, timeout * 1000); // http.socket.timeout

        HttpPost request = new HttpPost(API_URL +path);
        StringEntity entity = new StringEntity(data);
        request.addHeader("content-type", "application/x-www-from-urlencoded");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        return response.toString();
    }

    public String sendGet(String path) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        //timeout si la connexion prend trop de temps
        int timeout = 5; // seconds
        HttpParams httpParams = client.getParams();
        HttpConnectionParams.setConnectionTimeout(
                httpParams, timeout * 1000); // http.connection.timeout
        HttpConnectionParams.setSoTimeout(
                httpParams, timeout * 1000); // http.socket.timeout

        HttpGet request = new HttpGet(URL +path);

        HttpResponse response = client.execute(request);
        return response.toString();
    }

    public static void setURL(String URL) {
        HttpManager.URL = URL;
    }

    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }


}
