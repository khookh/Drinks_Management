
package controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.defonce_management.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import model.Initialize;
import model.SignIn;

//todo : implement fragment for sign in and sign up


public class WelcomePage extends AppCompatActivity {

    EditText nickname, password;
    TextView errormessage;

    static Initialize init = new Initialize();

    public WelcomePage(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        nickname = (EditText)findViewById(R.id.nickname);
        password = (EditText)findViewById(R.id.password);
        errormessage =(TextView)findViewById(R.id.errormessage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.contact_dev), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Is called by the Sign In button from content_welcome.xml
     * Call Methods in Sign In to check if the user exists
     * Then move to new Activity
     * @param view
     */
    public void cSign(View view) {
        SignIn si = new SignIn(nickname.getText().toString().trim(),password.getText().toString().trim(),getInit());
        si.start(); //lance le thread qui envoie la requête au serveur

        goToSignIn(); //test
    }
    public void goToSignIn(){ //called when is notified
        startActivity(new Intent(WelcomePage.this, Session_Control.class));
    }

    /**
     * Is called by the Sign Up button from content_welcome.xml
     * Move to the Sign Up activity
     * @param view
     */
    public void goToSignUp(View view){
        startActivity(new Intent(WelcomePage.this, SignUp_Control.class));
    }

    public static Initialize getInit() {
        return init;
    }
}

