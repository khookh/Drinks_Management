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
import model.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Overview extends Fragment {

    TextView lasttext;
    Session session;
    View root;
    Spinner days;
    LocalDateTime dateText = LocalDateTime.now().minusDays(1);
    ArrayList<TextView> consumptionText = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.overview, container, false);
        days = root.findViewById(R.id.spinner1);
        over();
        updateText();
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void over(){
        spinner();
    }
    public void spinner(){
        days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedDays = days.getSelectedItem().toString();
                //days.setPrompt(days.getSelectedItem().toString());
                if(selectedDays.equals("day")){
                    dateText = LocalDateTime.now().minusDays(1);
                }
                else if(selectedDays.equals("3 days")){
                    dateText = LocalDateTime.now().minusDays(3);
                }
                else if(selectedDays.equals("week")){
                    dateText = LocalDateTime.now().minusWeeks(1);
                }
                else if(selectedDays.equals("month")){
                    dateText = LocalDateTime.now().minusMonths(1);
                }
                updateText();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createText(LocalDateTime date){
        ScrollView hsv1 = root.findViewById( R.id.scrollView);
        LinearLayout layout = hsv1.findViewById(R.id.linear);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
        ArrayList<String> list = this.session.returnstringfromdate(date);
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
        createText(dateText);
    }
    public void setSession(Session session) {
        this.session = session;
    }

}
