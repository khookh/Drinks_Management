package controller;

import model.SignUp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.defonce_management.R;

import java.util.concurrent.TimeUnit;

/**
 * Control the Sign_Up activity
 */
public class SignUp_Control extends AppCompatActivity {
    EditText nickname, email, password, age, weight;
    CheckBox checkterms;
    TextView errormessage;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;


    String errormessage1 = getString(R.string.error);
    String errormessage2 = getString(R.string.email_already);
    String errormessage3 = getString(R.string.nickname_already);
    String errormessage4 = getString(R.string.Signed_Up);

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
    public void signUp(View view) throws InterruptedException {
        //TODO: rajouter + réécrire les conditions de manière plus propre

        String name = nickname.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String ag = age.getText().toString().trim();
        String wg = weight.getText().toString().trim();

        if(name.isEmpty()){
            errormessage.setText(R.string.you_must_nickname);
        }
        else if(mail.isEmpty()){
            errormessage.setText(R.string.you_must_email);
        }
        else if(pass.isEmpty()){
            errormessage.setText(R.string.you_must_pass);
        }
        else if(pass.length()<6){
            errormessage.setText(R.string.you_pass_6);
        }
        else if(ag.isEmpty()){
            errormessage.setText(R.string.you_must_age);
        }
        else if(wg.isEmpty()){
            errormessage.setText(R.string.you_must_weight);
        }
        else if (Integer.parseInt(ag) < 18) {
            errormessage.setText(R.string.you_must_18);
        }
        else if(!checkterms.isChecked()){
            errormessage.setText(R.string.you_must_terms);
        }
        else{
            int selectedId = radioSexGroup.getCheckedRadioButtonId(); //trouve l'id du button qui a été checké pour le sexe
            radioSexButton = (RadioButton) findViewById(selectedId);
            String sex = radioSexButton.getText().toString();

            SignUp su = new SignUp(name,mail,pass,Integer.parseInt(ag),sex,Double.parseDouble(wg), WelcomePage.getJsonHandler());
            su.setErrormessage4(errormessage4);
            String signed = su.getSignedup();

            errormessage.setText(signed);
            if(signed.equals(errormessage4)){ //si pas d'erreur
                TimeUnit.SECONDS.sleep(1);
                startActivity(new Intent(SignUp_Control.this, WelcomePage.class)); //retourne à la page d'acceuil
            }
        }
    }

    /**
     *Getter implemented to avoid hardcoding strings
     */


}
