
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


public class WelcomePage extends AppCompatActivity {

    EditText nickname, password;
    TextView errormessage;

    static Initialize init = new Initialize();

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
    public void goToSignIn(View view) throws InterruptedException {
        SignIn si = new SignIn(nickname.getText().toString().trim(),password.getText().toString().trim());
        errormessage.setText(si.getSignedin());
        if(si.getSignedin().equals("Signed In")){
            startActivity(new Intent(WelcomePage.this, Session_Control.class));
        }
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
