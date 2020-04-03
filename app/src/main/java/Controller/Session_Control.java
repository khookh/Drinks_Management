package Controller;

import Controller.Fragment.Consumption;
import Controller.Fragment.Overview;
import Controller.Fragment.SectionsPagerAdapter;
import Model.Session;
import Model.User;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.defonce_management.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * Manage the display and acquisition of data into the session page (+Consumption and Overview panels)
 */
public class Session_Control extends AppCompatActivity {

    static User actual_user = WelcomePage.getActual_user();
    TabLayout tabLayout;
    ViewPager viewPager;
    Button selectedalcool;
    static Consumption cons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);

        TabLayout tabs = findViewById(R.id.tabs);
        FloatingActionButton fab = findViewById(R.id.fab);

        cons = new Consumption();
        sectionsPagerAdapter.addFragment(cons, "Alcool Consumption");
        sectionsPagerAdapter.addFragment(new Overview(), "Overview");
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);

    }

    /**
     * Load the needed information into the consumption objects at creation
     * Called into Consumption.java
     */
    public static void startup_cons(){
        System.out.print("startup");
        cons.setTextLabel("Hello "+actual_user.getName()+" !" +"\n"+"Your alcool blood level is now around "+actual_user.getAlcoolRate()+" g/L");
        Session.setSkren(actual_user);
        cons.setProgressBar(Session.getSkrenlevel());
        cons.setTextBar(Session.getSkrenmessage());
    }
    public void selectedAlcool(View view){
        selectedalcool = (Button) findViewById(view.getId());
        System.out.println("Selected alcool is"+ selectedalcool.getText().toString());
    }
    //TODO: When the button 'add consumption' is pressed : verify if an alcool is selected or the field completed
    //TODO: If an alcool has been selected or the field completed, proceed to create an alcool, and asign it to the user with a time stamp

}
