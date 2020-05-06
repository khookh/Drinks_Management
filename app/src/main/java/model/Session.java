package model;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.RequiresApi;
import controller.Session_Control;
import model.threads.AddAlcoolRateThread;
import model.threads.ProcessAlcoolThread;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Implements methods to manage Data displayed by Session_Control
 */
public class Session {
    private ProcessAlcoolThread virtualfoie;
    private JSONHandler js;
    private String skrenmessage;
    private Integer skrenlevel;
    User actual_user ;
    TreeMap<String, Alcool> map;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Session(JSONHandler js) {
        this.js = js;
        this.actual_user = js.getActiveUser();
        actual_user.setAlcoolRate(0.0);
        virtualfoie = new ProcessAlcoolThread(js,this); //create the virtual foie at launch of the session
        Timer timer = new Timer();
        timer.schedule(virtualfoie,60000,60000); //1 call each minute
    }


    /**
     * Create new alcohol and the time it has been consumed and add it to the actual_user
     * @param bevname beverage name
     * @param volume beverage volume
     * @param percent beverage alcoolic concentration
     */
    @SuppressLint("NewApi")
    public void addAlcohol(String bevname, Double volume, Double percent, Boolean custom, Boolean eating) { //works
        Alcool new_alcohol = new Alcool(bevname,volume,percent);
        String time=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        actual_user.addConsumption(time,new_alcohol);
        if(custom && checkIfCustomAA(new_alcohol,actual_user) ){
            actual_user.addCustom(new_alcohol);
        }
        js.updateUser(actual_user);
        AddAlcoolRateThread alcoolThreadTimer = new AddAlcoolRateThread(new_alcohol,js,this,eating);
        Timer timer = new Timer();
        timer.schedule(alcoolThreadTimer,60000,60000); //1 call each minute

    }

    /**
     * check if a custom alcool is already linked to the user
     * @param a
     * @param u
     */
    public boolean checkIfCustomAA(Alcool a, User u){
        boolean already = true;
        for(int i = 0; i< u.getCustomAlcool().size(); i ++){
            if(u.getCustomAlcool().get(i).equals(a)){
                already = false;
            }
        }
        return already;
    }
    /**
     * Determine de level of skren and generate the informations to display by Session_Control (skren bar)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setSkren(){
        //TODO: add all the skren levels
        Double alcoolrate = actual_user.getAlcoolRate();
        setSkrenlevel((int) (alcoolrate/2.5*100));
        if (alcoolrate == 0.0){
            setSkrenmessage(Session_Control.getSkrenmessage1());
        }
        else if(alcoolrate>0.0 && alcoolrate<=0.3){
            setSkrenmessage(Session_Control.getSkrenmessage2());
        }
        else if(alcoolrate >0.3 && alcoolrate <=0.5){
            setSkrenmessage("Vision field reduced and perturbation in gestures");
        }
        else if(alcoolrate >0.5 && alcoolrate <=0.8){
            setSkrenmessage("Vision blured, euphoria, loss of reflexes");
        }
        else if(alcoolrate >0.8 && alcoolrate <=1.5){
            setSkrenmessage("Drunkenness, excitation");
        }
        else if(alcoolrate >1.5 && alcoolrate <=3){
            setSkrenmessage("Staggered walk, double vision");
        }
    }
    /**
     * @return message: string indiquant quelle est la dernière boisson
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String returnldstring() {
        map = new TreeMap<String, Alcool>(getActual_user().getConsumption()); //comme hashmap mais avec capacité de sorting
        String message = "";
        LocalDateTime weekago = LocalDateTime.now().minusWeeks(1);
        if(!map.isEmpty()){
            Alcool lastdrink = map.lastEntry().getValue();
            message = "Your last drink was : " + lastdrink.getName();
        }
        return message ;
    }

    /**
     * @param date
     * @return a treemap containing only alcools consumed after date
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TreeMap returnTMfromdate(LocalDateTime date){
        map = new TreeMap<String, Alcool>(getActual_user().getConsumption());
        TreeMap sortedmap = new TreeMap<String, Alcool>();
        Set set = map.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            LocalDateTime keytime = LocalDateTime.parse((String) entry.getKey(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if(keytime.isAfter(date)){
                sortedmap.put(entry.getKey(),entry.getValue());
            }
        }
        return sortedmap;
    }

    /**
     * @param date
     * @return an array list of strings corresponding to previous alcoolic consumption before given date
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> returnstringfromdate(LocalDateTime date){
        ArrayList<String> messagelist = new ArrayList<>();
        TreeMap sortedmap = returnTMfromdate(date);
        Set set = sortedmap.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next() ;
            Alcool alcool = (Alcool) entry.getValue() ;
            LocalDateTime time = LocalDateTime.parse((String) entry.getKey(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String message = alcool.getName() + "    --------    " + time.getDayOfMonth() + " " + time.getMonth() + " " + time.getHour() + "H" + time.getMinute();
            messagelist.add(message);
        }
        return messagelist;
    }



    public String getSkrenmessage() {
        return skrenmessage;
    }
    public void setSkrenmessage(String skrenmessage) {
        this.skrenmessage = skrenmessage;
    }

    public Integer getSkrenlevel() {
        return skrenlevel;
    }
    public void setSkrenlevel(Integer skrenlevel) {
        this.skrenlevel = skrenlevel;
    }

    public void setActual_user(User actual_user) {
        this.actual_user = actual_user;
    }
    public User getActual_user() {
        return actual_user;
    }
    public ProcessAlcoolThread getVirtualfoie() {
        return virtualfoie;
    }



}

