package controller.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.defonce_management.R;
import model.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Overview extends Fragment {

    TextView lasttext;
    Session session;
    View root;
    ArrayList<TextView> consumptionText = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.overview, container, false);
        lasttext = root.findViewById(R.id.lastd);
        over();
        updateText();
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void over(){
        String ld = this.session.returnldstring();
        setLastDText(ld);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createText(){
        ScrollView hsv1 = root.findViewById( R.id.scrollView);
        LinearLayout layout = hsv1.findViewById(R.id.linear);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
        LocalDateTime weekago = LocalDateTime.now().minusWeeks(1);
        ArrayList<String> list = this.session.returnstringfromdate(weekago);
        for(int i = list.size()-1 ; i >= 0 ; i--) {
            TextView textView = new TextView(root.getContext());
            textView.setLayoutParams(lp);
            textView.setText(list.get(i));
            textView.setId(-i);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            layout.addView(textView);
            consumptionText.add(textView);
        }
    }

    /**
     * update the list of string displaying previous consumption
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateText(){
        ScrollView hsv1 = root.findViewById( R.id.scrollView);
        LinearLayout layout = hsv1.findViewById(R.id.linear);
        if(!consumptionText.isEmpty()) {
            for (int i = 0; i < consumptionText.size(); i++) {
                layout.removeView(consumptionText.get(i));
            }
        }
        createText();
    }
    public void setLastDText(String texte) {
        this.lasttext.setText(texte);
    }
    public void setSession(Session session) {
        this.session = session;
    }

}
