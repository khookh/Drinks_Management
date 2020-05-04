package controller.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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
import model.Session;

/**
 * Implements the controls of the Consumption fragment displayed
 */
public class Consumption extends Fragment {
    View root;
    Session session;
    TextView bar;
    ProgressBar skren;
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

        setRoot(root);

        cons();
        return root;
    }

    //test
    public void addButton(Context tis){
        @SuppressLint("WrongViewCast") HorizontalScrollView hsv1 = root.findViewById( R.id.scrollView);
        LinearLayout layout = hsv1.findViewById(R.id.linear);
        layout.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
        lp.setMargins( 20, 0, 20, 0 );
        Button button = new Button(tis);
        button.setLayoutParams(lp);
        button.setGravity( Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL );
        button.setText("TEST TEST TEST");
        layout.addView(button);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cons(){
        this.session.setSkren();
        setProgressBar(this.session.getSkrenlevel());
        setTextBar("Your alcool blood level is now around "+(double) Math.round(session.getActual_user().getAlcoolRate()*100)/100+" g/L" +"\n"+ session.getSkrenmessage());
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
    public View getRoot() {
        return root;
    }

    public void setRoot(View root) {
        this.root = root;
    }
}
