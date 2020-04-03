package Controller;

import Controller.Fragment.Consumption;
import Controller.Fragment.Overview;
import Controller.Fragment.SectionsPagerAdapter;
import Model.PredefinedAlcohol.Classic25Pils;
import Model.PredefinedAlcohol.VodkaShot;
import Model.Session;
import Model.User;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
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
    SectionsPagerAdapter sectionsPagerAdapter;
    Button selectedalcool;
    static Consumption cons;
    static Overview ov;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        setSectionsPagerAdapter(sectionsPagerAdapter);
        ViewPager viewPager = findViewById(R.id.view_pager);
        setViewPager(viewPager);

        TabLayout tabs = findViewById(R.id.tabs);
        FloatingActionButton fab = findViewById(R.id.fab);

        cons = new Consumption();
        ov = new Overview();
        sectionsPagerAdapter.addFragment(cons, "Alcool Consumption");
        sectionsPagerAdapter.addFragment(ov, "Overview");
        viewPager.setAdapter(sectionsPagerAdapter); //initialise les fragment dans le viewpager -> fonctionne  aussi pour refresh les items
        tabs.setupWithViewPager(viewPager);

        //Touch Listener detecte .... les touch , utilisé pour refresh les fragments
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getViewPager().getAdapter().notifyDataSetChanged();
                System.out.println("TOUCH LISTENED");
                return false;
            }
        });
    }

    /**
     * Load the needed information into the consumption objects at creation
     * Called into Consumption.java
     */
    public static void startup_cons(){
        Session.setSkren(actual_user);
        cons.setProgressBar(Session.getSkrenlevel());
        cons.setTextBar("Your alcool blood level is now around "+actual_user.getAlcoolRate()+" g/L" +"\n"+ Session.getSkrenmessage());
    }

    /**
     * Called when an alcohol is selectionned in the Consumption fragment
     * @param view
     */
    public void selectedAlcool(View view){
        if(selectedalcool!=null){ //si il y a déjà un alcool selectionné, le réeaffiche comme non sélectionné
            selectedalcool.setTextColor(Color.BLACK);
        }
        selectedalcool = (Button) findViewById(view.getId());
        selectedalcool.setTextColor(Color.WHITE); //affiche l'alcool comme selectionné
    }

    /**
     * Called when the user push to "add consumption" in consumption fragment
     * See if it's a predetermined alcohol or not
     * @param view
     */
    public void addconsumption(View view){
        if(selectedalcool!=null){
            if(selectedalcool.getTag().equals("Classic25Pils")){
                Session.addAlcohol(Classic25Pils.getName(),Classic25Pils.getVolume(),Classic25Pils.getPercentage());
            }
            else if(selectedalcool.getTag().equals("VodkaShot")){
                Session.addAlcohol(VodkaShot.getName(),VodkaShot.getVolume(),VodkaShot.getPercentage());
            }
            //TODO ; add predifined alcohol
        }
        else{
            Session.addAlcohol(cons.getBevname(),Double.parseDouble(cons.getVolume()), Double.parseDouble(cons.getPercent()));
        }
    }

    //TODO : start implementing overview, by showing the lasts drinks by example

    /**
     * Load the needed information into the consumption objects at creation
     * Called into Overview.java
     */
    public static void startup_ov(){
        String ld = Session.returnldstring();
        ov.setLastDText(ld);

    }

    public SectionsPagerAdapter getSectionsPagerAdapter() {
        return sectionsPagerAdapter;
    }

    public void setSectionsPagerAdapter(SectionsPagerAdapter sectionsPagerAdapter) {
        this.sectionsPagerAdapter = sectionsPagerAdapter;
    }
    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
