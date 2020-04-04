package model;

import controller.Session_Control;
import android.annotation.SuppressLint;
import android.util.Pair;

import java.time.LocalDateTime;

/**
 * Implements methods to manage Data displayed by Session_Control
 */
public class Session {

    private static String skrenmessage;
    private static Integer skrenlevel;
    private static User actual_user;
    private static Pair<LocalDateTime, Alcool> lastdrink; //temporaire

    /**
     * Create new alcohol and the time it has been consumed and add it to the actual_user
     * @param bevname
     * @param volume
     * @param percent
     */
    @SuppressLint("NewApi")
    public static void addAlcohol(String bevname, Double volume, Double percent) { //works
        Alcool new_alcohol = new Alcool(bevname,volume,percent);
        actual_user.setLastdrink(LocalDateTime.now(),new_alcohol); //set la dernière boisson bu par le user
        actual_user.addConsumption(LocalDateTime.now(),new_alcohol);
        //updatebloodlevel(); //temporary
    }
    /**
     * Determine de level of skren and generate the informations to display by Session_Control (skren bar)
     * @param actual_user
     */
    public static void setSkren(User actual_user){
        setActual_user(actual_user);
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
    public static String returnldstring() {
        String message = "";
        Pair<LocalDateTime, Alcool> ld = getActual_user().getLastdrink(); //à remplacer par autre chose quand il y aura persistance
        if(ld!=null){
            Alcool alcool = ld.second;
            message = "Your last drink was : " + alcool.getName();
        }
        return message ;
    }

    public static String getSkrenmessage() {
        return skrenmessage;
    }
    public static void setSkrenmessage(String skrenmessage) {
        Session.skrenmessage = skrenmessage;
    }

    public static Integer getSkrenlevel() {
        return skrenlevel;
    }
    public static void setSkrenlevel(Integer skrenlevel) {
        Session.skrenlevel = skrenlevel;
    }

    public static void setActual_user(User actual_user) {
        Session.actual_user = actual_user;
    }
    public static User getActual_user() {
        return actual_user;
    }

}

