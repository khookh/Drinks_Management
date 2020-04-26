package model;

import android.annotation.SuppressLint;
import android.util.Pair;
import controller.Session_Control;

import java.time.LocalDateTime;

/**
 * Implements methods to manage Data displayed by Session_Control
 */
public class Session {

    private Initialize init;
    private String skrenmessage;
    private Integer skrenlevel;
    private Pair<LocalDateTime, Alcool> lastdrink; //temporaire
    User actual_user ;

    public Session(Initialize init) {
        this.init = init;
        this.actual_user = init.getActual_user();
    }

    public Pair<LocalDateTime, Alcool> getLastdrink() {
        return lastdrink;
    }

    public void setLastdrink(Pair<LocalDateTime, Alcool> lastdrink) {
        this.lastdrink = lastdrink;
    }



    /**
     * Create new alcohol and the time it has been consumed and add it to the actual_user
     * @param bevname
     * @param volume
     * @param percent
     */
    @SuppressLint("NewApi")
    public void addAlcohol(String bevname, Double volume, Double percent) { //works
        Alcool new_alcohol = new Alcool(bevname,volume,percent);
        actual_user.setLastdrink(LocalDateTime.now(),new_alcohol); //set la dernière boisson bu par le user
        actual_user.addConsumption(LocalDateTime.now(),new_alcohol);
        //updatebloodlevel(); //temporary
    }
    /**
     * Determine de level of skren and generate the informations to display by Session_Control (skren bar)
     */
    public void setSkren(){
        //TODO: add all the skren levels
        Double alcoolrate = actual_user.getAlcoolRate();
        if (alcoolrate == 0.0){
            setSkrenlevel(0);
            setSkrenmessage(Session_Control.getSkrenmessage1());
        }
        else if(alcoolrate>0.0 && alcoolrate<=0.1){
            setSkrenlevel(5);
            setSkrenmessage(Session_Control.getSkrenmessage2());
        }
    }
    /**
     * @return message: string indiquant quelle est la dernière boisson
     */
    public String returnldstring() {
        String message = "";
        Pair<LocalDateTime, Alcool> ld = getActual_user().getLastdrink(); //à remplacer par autre chose quand il y aura persistance
        if(ld!=null){
            Alcool alcool = ld.second;
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

    public Initialize getInit() {
        return init;
    }


}

