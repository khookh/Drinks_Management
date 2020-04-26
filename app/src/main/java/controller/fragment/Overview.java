package controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.defonce_management.R;
import model.Session;

public class Overview extends Fragment {

    TextView lasttext;

    Session session;
    View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.overview, container, false);
        lasttext = root.findViewById(R.id.lastd);

        String ld = this.session.returnldstring();
        setLastDText(ld);

        return root;
    }

    public void setLastDText(String texte) {
        this.lasttext.setText(texte);
    }
    public void setSession(Session session) {
        this.session = session;
    }

}
