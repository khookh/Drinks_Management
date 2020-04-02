package Controller.Fragment;

import Controller.Session_Control;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.defonce_management.R;

public class Consumption extends Fragment {

    TextView label;

    // User actual_user = WelcomePage.getActual_user();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.consumption, container, false);
        label = root.findViewById(R.id.cons_label);
        System.out.println("oncreateview");

        Session_Control.startup_cons();

        return root;
    }


    public void setTextLabel(String text) {
        label.setText(text);
    }
}
