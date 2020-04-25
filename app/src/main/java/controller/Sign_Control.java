
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
import model.SignIn;
import model.User;

import java.util.ArrayList;


public class Sign_Control extends AppCompatActivity {

    private static User actual_user;
    public static ArrayList<User> users = new ArrayList();
    EditText nickname, password;
    TextView errormessage;

    //TODO: important , separate controller/network/model from this class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);

        //TEMPORARY FOR TEST
        User newuser = new User("Stefano","e",85.6,23,"123456","boi");
        newuser.setAlcoolRate(0.1);
        users.add(newuser);
        setUsers(users);


        //TEMPORARY FOR TEST

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

    public static ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
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
    //todo: implement observer/observable to notify when the server send a response
    public void cSignIn(View view) throws InterruptedException {
        SignIn si = new SignIn(nickname.getText().toString().trim(),password.getText().toString().trim());
        si.start();
        errormessage.setText(si.getSignedin());
        //temporaire
        goToSignIn(si.getSignedin());
    }

    //todo: call it when signedin is updated in SignIn.java (observer/observable)
    public void goToSignIn(String signedin){
        if(signedin.equals("Signed In")){
            startActivity(new Intent(Sign_Control.this, Session_Control.class));
        }
    }


    /**
     * Is called by the Sign Up button from content_welcome.xml
     * Move to the Sign Up activity
     * @param view
     */
    public void goToSignUp(View view){
        startActivity(new Intent(Sign_Control.this, SignUp_Control.class));
    }

    public static User getActual_user() {
        return actual_user;
    }
    public void setActual_user(User actual_user) {
       this.actual_user = actual_user;
    }
}
