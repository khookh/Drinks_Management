package controller.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.defonce_management.R;
import controller.Session_Control;
import model.Alcool;
import model.Session;

import java.util.ArrayList;

/**
 * Implements the controls of the Consumption fragment displayed
 */
public class Consumption extends Fragment {
    View root;
    Session session;
    ArrayList<Button> customButton = new ArrayList<>();
    Session_Control session_control;
    TextView bar, consumption;
    ProgressBar skren;
    Switch eating;
    EditText volume, percent, bevname;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.consumption, container, false);
        bar = root.findViewById(R.id.barcomment);
        skren = root.findViewById(R.id.progressBar);
        volume = root.findViewById(R.id.volume);
        percent = root.findViewById(R.id.percent);
        bevname = root.findViewById(R.id.bevname);
        eating = root.findViewById(R.id.switch1);
        consumption = root.findViewById(R.id.consumptiontext);
        updateButton();
        setRoot(root);
        cons();
        return root;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cons(){
        setConsText("");
        this.session.setSkren();
        setProgressBar(this.session.getSkrenlevel());
        setTextBar("Your alcool blood level is now around "+(double) Math.round(session.getActual_user().getAlcoolRate()*100)/100+" g/L" +"\n"+ session.getSkrenmessage());
    }

    public void createButton(){
        ScrollView hsv1 = root.findViewById( R.id.scrollView);
        LinearLayout layout = hsv1.findViewById(R.id.linear);
        //layout.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
        ArrayList<Alcool> list = getCustomCons();
        for(int i = 0 ; i < list.size() ; i++) {
            Button button = buttonSetting(list.get(i),i);
            button.setLayoutParams(lp);
            layout.addView(button);
            customButton.add(button);
        }
    }
    public ArrayList<Alcool> getCustomCons(){
        return session.getActual_user().getCustomAlcool();
    }
    public Button buttonSetting(Alcool customAlcool,int i){
        Button button = new Button(root.getContext());;
        button.setId(i);
        button.setTag(customAlcool);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                session_control.selectedAlcool(v);
            }
        });
        button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        button.setText(customAlcool.getName());
        return button;
    }

    /**
     * update the list of buttons corresponding to custom consumption
     */
    public void updateButton(){
        ScrollView hsv1 = root.findViewById( R.id.scrollView);
        LinearLayout layout = hsv1.findViewById(R.id.linear);
        if(!customButton.isEmpty()) {
            for (int i = 0; i < customButton.size(); i++) {
                layout.removeView(customButton.get(i));
            }
        }
        createButton();
    }

    /**
     * set the text to display under the the skren bar
     * @param text
     */
    public void setTextBar(String text) { bar.setText(text); }

    /**
     * set the progress of the skren bar
     * @param progress
     */
    public void setProgressBar(Integer progress) { skren.setProgress(progress); }
    public void setConsText(String txt) {consumption.setText(txt);}
    public String getVolume() {
        return volume.getText().toString();
    }
    public String getPercent() {
        return percent.getText().toString();
    }
    public String getBevname() {
        return bevname.getText().toString();
    }

    public void setSession(Session session) {
        this.session = session;
    }
    public Switch getEating() {
        return eating;
    }
    public void setRoot(View root) {
        this.root = root;
    }
    public void setSession_control(Session_Control session_control) {
        this.session_control = session_control;
    }
    public void clearFields(){
        bevname.setText("");
        volume.setText("");
        percent.setText("");
        eating.setChecked(false);
    }

}
