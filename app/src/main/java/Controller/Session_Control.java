package Controller;

import Controller.Fragment.Consumption;
import Controller.Fragment.Overview;
import Controller.Fragment.SectionsPagerAdapter;
import Model.User;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.defonce_management.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class Session_Control extends AppCompatActivity {

    static User actual_user = WelcomePage.getActual_user();
    TabLayout tabLayout;
    ViewPager viewPager;
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
    public static void startup_cons(){
        System.out.print("startup");
        cons.setTextLabel("Hello "+actual_user.getName()+" !" +"\n"+"Your alcool blood level is now around "+actual_user.getAlcoolRate()+" g/L");
    }

}
