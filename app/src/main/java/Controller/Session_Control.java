package Controller;

import Controller.Fragment.Alcool_Consumption;
import Controller.Fragment.SectionsPagerAdapter;
import Controller.Fragment.Overview;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.defonce_management.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class Session_Control extends AppCompatActivity {


    TextView sectionlabel;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        sectionsPagerAdapter.addFragment(new Alcool_Consumption(), "Alcool Consumption");
        sectionsPagerAdapter.addFragment(new Overview(), "Overview");
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);

        startup();

    }

    public void startup(){
        // sectionlabel.setText("Hello "+actual_user.getName()+" !" +"\n"+"Your alcool blood level is now around "+actual_user.getAlcoolRate()+" g/L");
    }

}
