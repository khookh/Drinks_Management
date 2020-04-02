package Controller;

import Model.User;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.defonce_management.R;

public class Session_Control extends AppCompatActivity {
    User actual_user = WelcomePage.getActual_user();
    TextView startup;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session);
        startup = (TextView) findViewById(R.id.startup);

        startup();
    }
    public void startup(){
        startup.setText("Welcome "+actual_user.getName()+" !" +"\n"+"Your alcool blood level is now around "+actual_user.getAlcoolRate()+" g/L");
    }

}
