package Controller;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.defonce_management.R;

public class SignUp  extends AppCompatActivity {
    private EditText nickame;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    public void SignUp(View view) {
        System.out.println(nickame.getText()); //test syntaxe

    }
}
