package Controller;

import Controller.Fragment.SectionsPagerAdapter;
import Model.User;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.defonce_management.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class Session_Control extends AppCompatActivity {


    User actual_user = WelcomePage.getActual_user();
    TextView startup;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        //startup = (TextView) findViewById(R.id.startup);
        startup();

    }

    public void startup(){
        //startup.setText("Welcome "+actual_user.getName()+" !" +"\n"+"Your alcool blood level is now around "+actual_user.getAlcoolRate()+" g/L");
    }

}
