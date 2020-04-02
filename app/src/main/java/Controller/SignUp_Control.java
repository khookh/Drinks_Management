package Controller;

import Model.SignUp;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.defonce_management.R;

public class SignUp_Control extends AppCompatActivity {
    EditText nickname, email, password, age;
    CheckBox checkterms;
    TextView errormessage;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        nickname = (EditText)findViewById(R.id.nickname);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        age = (EditText)findViewById(R.id.age);

        errormessage = (TextView)findViewById(R.id.errormessage);

        checkterms = (CheckBox) findViewById(R.id.checkterms);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }


    /**
     * Called by the Sign Up button from content_sign_up.xml
     * Verify if all fields are correctly completed and then proceed to sign up
     * @param view
     */
    public void SignUp(View view) {
        //TODO: rajouter + réécrire les conditions de manière plus propre

        String name = nickname.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String ag = age.getText().toString().trim();

        if(name.isEmpty()){
            errormessage.setText("You must choose Nickname");
        }
        else if(mail.isEmpty()){
            errormessage.setText("You must put your email adress");
        }
        else if(pass.isEmpty()){
            errormessage.setText("You must put choose a password");
        }
        else if(ag.isEmpty()){
            errormessage.setText("You must put your age");
        }
        else if (Integer.parseInt(ag) < 18) {
            errormessage.setText("Your age must be above 18");
        }
        else if(!checkterms.isChecked()){
            errormessage.setText("You must accept the terms of agreement");
        }
        else{
            int selectedId = radioSexGroup.getCheckedRadioButtonId(); //trouve l'id du button qui a été checké
            radioSexButton = (RadioButton) findViewById(selectedId);
            String sex = radioSexButton.getText().toString();
            String signed = SignUp.SignUp(name,mail,pass,Integer.parseInt(ag),sex);
            if(signed.equals("Signed Up")){
                //change activity
            }
            else{
                errormessage.setText(signed); //écrit le message d'erreur du style "nickname déjà prit" ou "mail déjà existant"
            }
        }

    }
}
