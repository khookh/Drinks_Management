package Controller.Fragment;

import Controller.Session_Control;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.defonce_management.R;

/**
 * Implements the controls of the Consumption fragment displayed on session.xml
 */
public class Consumption extends Fragment {
    View root;
    TextView label;
    TextView bar;
    ProgressBar skren;


    // User actual_user = WelcomePage.getActual_user();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.consumption, container, false);
        label = root.findViewById(R.id.cons_label);
        bar = root.findViewById(R.id.barcomment);
        skren = root.findViewById(R.id.progressBar);
        System.out.println("oncreateview");

        Session_Control.startup_cons();

        return root;
    }

    public void setTextLabel(String text) {
        label.setText(text);
    }

    public void setTextBar(String text) {
        bar.setText(text);
    }

    public void setProgressBar(Integer progress) { skren.setProgress(progress); }
}
