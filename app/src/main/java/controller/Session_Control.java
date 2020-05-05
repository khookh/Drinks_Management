package controller;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.defonce_management.R;
import com.google.android.material.tabs.TabLayout;
import controller.fragment.Consumption;
import controller.fragment.Overview;
import controller.fragment.SectionsPagerAdapter;
import model.Session;
import model.predefinedAlcohol.Classic25Pils;
import model.predefinedAlcohol.VodkaShot;

import java.util.Observable;
import java.util.Observer;

/**
 * Manage the display and acquisition of data into the session page (+Consumption and Overview panels)
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class Session_Control extends AppCompatActivity implements Observer {

    //skrenmessage used to display user state
    static String skrenmessage1;
    static String skrenmessage2;

    //todo : create session in welcomepage after sign in
    Session session = new Session(WelcomePage.getJsonHandler());

    static ViewPager viewPager;
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

        WelcomePage.getJsonHandler().addObserver(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        setViewPager(viewPager);

        TabLayout tabs = findViewById(R.id.tabs);

        cons = new Consumption();
        cons.setSession(this.session);
        // cons.addButton(this); test
        ov = new Overview();
        ov.setSession(this.session);



        sectionsPagerAdapter.addFragment(cons, getString(R.string.Alcool_Consumption));
        sectionsPagerAdapter.addFragment(ov, getString(R.string.Overview));
        viewPager.setAdapter(sectionsPagerAdapter); //initialise les fragment dans le viewpager -
        tabs.setupWithViewPager(viewPager);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                refresh();
                deselectalcool();
                return false;
            }
        });
    }

    public static void refresh(){
        getViewPager().getAdapter().notifyDataSetChanged(); //refresh view
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
        if (selectedalcool != null){
            selectedalcool.setTextColor(Color.BLACK);
            selectedalcool = null;
        }
    }

    /**
     * Called when the user push to "add consumption" in consumption fragment
     * See if it's a predetermined alcohol or not
     * @param view
     */
    public void addconsumption(View view){
        if(selectedalcool!=null){
            if(selectedalcool.getTag().equals("Classic25Pils")){
                this.session.addAlcohol(Classic25Pils.getName(),Classic25Pils.getVolume(),Classic25Pils.getPercentage(),false);
            }
            else if(selectedalcool.getTag().equals("VodkaShot")){
                this.session.addAlcohol(VodkaShot.getName(),VodkaShot.getVolume(),VodkaShot.getPercentage(),false);
            }
            deselectalcool();
            //TODO ; add predifined alcohol
        }
        else if(!cons.getBevname().isEmpty() && !cons.getVolume().isEmpty() && !cons.getPercent().isEmpty() ){
            this.session.addAlcohol(cons.getBevname(),Double.parseDouble(cons.getVolume()), Double.parseDouble(cons.getPercent()),true);
        }
        getViewPager().getAdapter().notifyDataSetChanged(); //refresh data displayed
    }


    public static ViewPager getViewPager() {
        return viewPager;
    }
    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public static String getSkrenmessage1() { return skrenmessage1; }
    public static void setSkrenmessage1(String skrenmessage1) { Session_Control.skrenmessage1 = skrenmessage1; }

    public static String getSkrenmessage2() { return skrenmessage2; }
    public static void setSkrenmessage2(String skrenmessage2) { Session_Control.skrenmessage2 = skrenmessage2; }

    /**
     * Called when observable are updated
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        cons.cons();
    }
}
