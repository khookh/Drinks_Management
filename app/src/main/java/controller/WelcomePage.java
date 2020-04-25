
package controller;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import network.HttpManager;

import java.util.concurrent.TimeUnit;


public class WelcomePage extends AppCompatActivity {

    public static HttpManager hm;

    //TODO: important , separate controller/network/model from this class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpManager hm = new HttpManager("URL","API_URL");
        setHm(hm);
        try {
            goToSign();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void goToSign() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        startActivity(new Intent(WelcomePage.this, Sign_Control.class));
    }

    public static HttpManager getHm() {
        return hm;
    }
    public static void setHm(HttpManager hm) {
        WelcomePage.hm = hm;
    }
}
