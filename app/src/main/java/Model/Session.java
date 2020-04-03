package Model;

/**
 *
 */
public class Session {

    private static String skrenmessage;
    private static Integer skrenlevel;
    public void Session() {

    }

    /**
     * Determine de level of skren and generate the informations to display by Session_Control
     * @param actual_user
     */
    public static void setSkren(User actual_user){
        //TODO: add all the skren levels
        Double alcoolrate = actual_user.getAlcoolRate();
        if (alcoolrate == 0.0){
            setSkrenlevel(0);
            setSkrenmessage("You are sober");
        }
        else if(alcoolrate>0.0 && alcoolrate<=0.1){
            setSkrenlevel(5);
            setSkrenmessage("Almost sober");
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
}
