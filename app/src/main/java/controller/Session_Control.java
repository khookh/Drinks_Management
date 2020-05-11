package controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.defonce_management.R;
import com.google.android.material.tabs.TabLayout;
import controller.fragment.Consumption;
import controller.fragment.Overview;
import controller.fragment.SectionsPagerAdapter;
import model.Alcool;
import model.JSONHandler;
import model.Session;
import model.predefinedAlcohol.Classic25Pils;
import model.predefinedAlcohol.VodkaShot;
import model.threads.BackGroundServiceAddAlc;
import model.threads.BackGroundServiceLiver;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Manage the display and acquisition of data into the session page (+Consumption and Overview panels)
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class Session_Control extends AppCompatActivity implements Observer {

    static String skrenmessage1;
    static String skrenmessage2;
    ArrayList<Intent> servicesIntent = new ArrayList<>();
    Session session = new Session(WelcomePage.getJsonHandler());

    static ViewPager viewPager;
    Button selectedalcool;

    Consumption cons;
    Overview ov;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createLiver();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.session);

        setSkrenmessage1(getString(R.string.skrenmsg1));
        setSkrenmessage2(getString(R.string.skrenmsg2));

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        setViewPager(viewPager);

        TabLayout tabs = findViewById(R.id.tabs);

        cons = new Consumption();
        cons.setSession(this.session);
        cons.setSession_control(this);
        // cons.addButton(this); test
        ov = new Overview();
        ov.setSession(this.session);

        WelcomePage.getJsonHandler().addObserver(this);

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

    /**
     * refresh les éléments attachés au sectionPagerAdapter
     */
    public static void refresh(){
        getViewPager().getAdapter().notifyDataSetChanged();
       //refresh view
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
        Boolean eating = cons.getEating().isChecked();
        String name = null;
        Alcool new_alcohol = null;
        if(selectedalcool!=null){
            if(selectedalcool.getTag().equals("Classic25Pils")){
                name = Classic25Pils.getName();
                new_alcohol = this.session.addAlcohol(name,Classic25Pils.getVolume(),Classic25Pils.getPercentage(),false);
            }
            else if(selectedalcool.getTag().equals("VodkaShot")){
                name = VodkaShot.getName();
                new_alcohol = this.session.addAlcohol(name,VodkaShot.getVolume(),VodkaShot.getPercentage(),false);
            }
            else if(selectedalcool.getTag().getClass().equals(Alcool.class)){
                Alcool custom = (Alcool)(selectedalcool.getTag());
                name = custom.getName();
                new_alcohol = this.session.addAlcohol(name,custom.getVolume(),custom.getPercentage(),false);
            }
            deselectalcool();
        }
        else if(!cons.getBevname().isEmpty() && !cons.getVolume().isEmpty() && !cons.getPercent().isEmpty() ){
            name = cons.getBevname();
            new_alcohol = this.session.addAlcohol(name,Double.parseDouble(cons.getVolume()), Double.parseDouble(cons.getPercent()),true);
        }
        cons.clearFields();
        cons.updateButton(); //updata custom consommation list
        ov.updateText();
        refresh();
        if(name!=null && new_alcohol!= null){
            createAddAlcool(new_alcohol,eating);
            Toast.makeText(this, "Consumption "+name+" has been added !", Toast.LENGTH_SHORT).show();
        }
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
        if(o instanceof JSONHandler) { //si json updaté on refresh les info affichées
            cons.cons();
            ov.over();
            //refresh();
        }
    }

    public void createLiver(){
        Intent liverIntent = new Intent(this, BackGroundServiceLiver.class);
        startService(liverIntent);
        servicesIntent.add(liverIntent);
    }

    public void createAddAlcool(Alcool alcool, Boolean eating){
        Intent serviceIntent = new Intent(this, BackGroundServiceAddAlc.class);
        serviceIntent.putExtra("alcool", alcool);
        serviceIntent.putExtra("eating",eating);
        startService(serviceIntent);
        servicesIntent.add(serviceIntent);
    }

    /**
     * called upon destruction of this activity
     */
    protected void onDestroy(){
        super.onDestroy();
        deleteServices();
        finish();
    }
    protected void deleteServices(){
        if(servicesIntent!=null) {
            for (int i = 0; i < servicesIntent.size(); i++) {
                stopService(servicesIntent.get(i));
                System.out.println("service "+i+" destoryed");
            }
        }
    }


}
