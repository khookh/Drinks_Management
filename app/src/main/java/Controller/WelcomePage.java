
package Controller;

import Model.SignIn;
import Model.User;
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

import java.util.ArrayList;

public class WelcomePage extends AppCompatActivity {

    public static ArrayList<User> users = new ArrayList();   //TEMPORARY FOR TEST
    EditText nickname, password;
    TextView errormessage;

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
                Snackbar.make(view, "Facing issues ? Improvement idea ? Contact the devs", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        WelcomePage.users = users;
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
    public void GoToSignIn(View view) throws InterruptedException {
        SignIn.SignIn(nickname.getText().toString().trim(),password.getText().toString().trim());
        errormessage.setText(SignIn.getSignedin());
        if(SignIn.getSignedin().equals("Signed In")){
            //startActivity(WelcomePage.this, Session_Control.class));
        }

    }

    /**
     * Is called by the Sign Up button from content_welcome.xml
     * Move to the Sign Up activity
     * @param view
     */
    public void GoToSignUp(View view){
        startActivity(new Intent(WelcomePage.this, SignUp_Control.class));
    }
}
