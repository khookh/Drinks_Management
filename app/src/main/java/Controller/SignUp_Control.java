package Controller;

import Model.SignUp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.defonce_management.R;
import java.util.concurrent.TimeUnit;

public class SignUp_Control extends AppCompatActivity {
    EditText nickname, email, password, age, weight;
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
        weight = (EditText)findViewById(R.id.weight);

        errormessage = (TextView)findViewById(R.id.errormessage);

        checkterms = (CheckBox) findViewById(R.id.checkterms);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }


    /**
     * Called by the Sign Up button from content_sign_up.xml
     * Verify if all fields are correctly completed and then proceed to sign up
     * @param view
     */
    public void SignUp(View view) throws InterruptedException {
        //TODO: rajouter + réécrire les conditions de manière plus propre

        String name = nickname.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String ag = age.getText().toString().trim();
        String wg = weight.getText().toString().trim();

        if(name.isEmpty()){
            errormessage.setText("You must choose Nickname");
        }
        else if(mail.isEmpty()){
            errormessage.setText("You must put your email adress");
        }
        else if(pass.isEmpty()){
            errormessage.setText("You must put choose a password");
        }
        else if(pass.length()<6){
            errormessage.setText("Your password must be at least 6 characters");
        }
        else if(ag.isEmpty()){
            errormessage.setText("You must put your age");
        }
        else if(wg.isEmpty()){
            errormessage.setText("Your must put your weight");
        }
        else if (Integer.parseInt(ag) < 18) {
            errormessage.setText("Your age must be above 18");
        }
        else if(!checkterms.isChecked()){
            errormessage.setText("You must accept the terms of agreement");
        }
        else{
            int selectedId = radioSexGroup.getCheckedRadioButtonId(); //trouve l'id du button qui a été checké pour le sexe
            radioSexButton = (RadioButton) findViewById(selectedId);
            String sex = radioSexButton.getText().toString();

            String signed = SignUp.SignUp(name,mail,pass,Integer.parseInt(ag),sex,Double.parseDouble(wg));
            errormessage.setText(signed);
            if(signed.equals("Signed Up")){ //si pas d'erreur
                TimeUnit.SECONDS.sleep(2);
                startActivity(new Intent(SignUp_Control.this, WelcomePage.class)); //retourne à la page d'acceuil
            }
        }

    }
}
