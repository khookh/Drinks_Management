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

    //skrenmessage used to display user state
    static String skrenmessage1;
    static String skrenmessage2;

    static User actual_user = WelcomePage.getActual_user();
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

        setSkrenmessage1(getString(R.string.skrenmsg1));
        setSkrenmessage2(getString(R.string.skrenmsg2));

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        setSectionsPagerAdapter(sectionsPagerAdapter);
        ViewPager viewPager = findViewById(R.id.view_pager);
        setViewPager(viewPager);

        TabLayout tabs = findViewById(R.id.tabs);
        FloatingActionButton fab = findViewById(R.id.fab);

        cons = new Consumption();
        ov = new Overview();

        sectionsPagerAdapter.addFragment(cons, getString(R.string.Alcool_Consumption));
        sectionsPagerAdapter.addFragment(ov, getString(R.string.Overview));
        viewPager.setAdapter(sectionsPagerAdapter); //initialise les fragment dans le viewpager -
        tabs.setupWithViewPager(viewPager);

        //Touch Listener detecte .... les touch , utilisé pour refresh les fragments
        //utilisé ici pour test
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getViewPager().getAdapter().notifyDataSetChanged();
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
     * Called when an alcohol is selected in the Consumption fragment (scrollview items)
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
     * Deselect logically and graphically the alcool
     */
    private void deselectalcool() {
        selectedalcool.setTextColor(Color.BLACK);
        selectedalcool=null;
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
            deselectalcool();
            //TODO ; add predifined alcohol
        }
        else{
            Session.addAlcohol(cons.getBevname(),Double.parseDouble(cons.getVolume()), Double.parseDouble(cons.getPercent()));
        }
        getViewPager().getAdapter().notifyDataSetChanged(); //refresh data displayed
    }



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

    public static String getSkrenmessage1() {
        return skrenmessage1;
    }

    public static void setSkrenmessage1(String skrenmessage1) {
        Session_Control.skrenmessage1 = skrenmessage1;
    }

    public static String getSkrenmessage2() {
        return skrenmessage2;
    }

    public static void setSkrenmessage2(String skrenmessage2) {
        Session_Control.skrenmessage2 = skrenmessage2;
    }
}
