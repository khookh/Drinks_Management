package model;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.RequiresApi;
import controller.Session_Control;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;

/**
 * Implements methods to manage Data displayed by Session_Control
 */
public class Session {
    private ProcessAlcoolThread virtualfoie;
    private JSONHandler js;
    private String skrenmessage;
    private Integer skrenlevel;
    User actual_user ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Session(JSONHandler js) {
        this.js = js;
        this.actual_user = js.getActiveUser();
        actual_user.setAlcoolRate(0.0);
        virtualfoie = new ProcessAlcoolThread(js,this);
        Timer timer = new Timer();
        timer.schedule(virtualfoie,60000,60000); //1 call each minute
    }


    /**
     * Create new alcohol and the time it has been consumed and add it to the actual_user
     * @param bevname
     * @param volume
     * @param percent
     */
    @SuppressLint("NewApi")
    public void addAlcohol(String bevname, Double volume, Double percent, Boolean custom, Boolean eating) { //works
        Alcool new_alcohol = new Alcool(bevname,volume,percent);
        String time=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        actual_user.setLastdrink(time); //set la dernière boisson bu par le user
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
    public String returnldstring() {
        String message = "";
        String time = getActual_user().getLastdrink(); //à remplacer par autre chose quand il y aura persistance
        if(time!=null){
            Alcool alcool = getActual_user().getConsumption().get(time); //return l'alcool correspond au time du last drink
            message = "Your last drink was : " + alcool.getName();
        }
        return message ;
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

