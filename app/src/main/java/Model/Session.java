package Model;

import android.annotation.SuppressLint;

import java.time.LocalDateTime;

/**
 *
 */
public class Session {

    private static String skrenmessage;
    private static Integer skrenlevel;
    private static User actual_user;


    public void Session() {

    }

    /**
     * Create new alcohol and the time it has been consumed and add it to the actual_user
     * @param bevname
     * @param volume
     * @param percent
     */
    @SuppressLint("NewApi")
    public static void addAlcohol(String bevname, Double volume, Double percent) { //works
        Alcool new_alcohol = new Alcool(bevname,volume,percent);
        actual_user.addConsumption(LocalDateTime.now(),new_alcohol);
    }
    /**
     * Determine de level of skren and generate the informations to display by Session_Control
     * @param actual_user
     */
    public static void setSkren(User actual_user){
        setActual_user(actual_user);
        //TODO: add all the skren levels
        Double alcoolrate = actual_user.getAlcoolRate();
        if (alcoolrate == 0.0){
            setSkrenlevel(0);
            setSkrenmessage("You are sober");
        }
        else if(alcoolrate>0.0 && alcoolrate<=0.1){
            setSkrenlevel(5);
            setSkrenmessage("You are almost sober");
        }
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

}

