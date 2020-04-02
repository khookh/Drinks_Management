package Model;

import Controller.WelcomePage;

import java.util.ArrayList;

public class SignUp {

    private static String signedup = "error";
    private static ArrayList<User> users = WelcomePage.getUsers();

    /**
     * SignUp instance; verify the information and proceed to sign up
     * @param nickname
     * @param email
     * @param password
     * @param age
     * @param sex
     * @param weight
     */
    public static void SignUp(String nickname, String email, String password, Integer age, String sex, Double weight){
        if(CheckUser(email,nickname)){
            CreateUser(nickname,email,password,age,sex,weight);
            setSignedup("Signed Up");
        }
    }

    /**
     * @return boolean check ; if the user already exist or not
     */
    private static boolean CheckUser(String e, String n) {
        boolean check = true;
        ArrayList<User> users = WelcomePage.getUsers();

        //check dans le stockage, arraylist sol. temporaire pour test

        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getEmail().equals(e)){
                check = false;
                setSignedup("Email adress already used");
            }
            if(users.get(i).getName().equals(n)){
                check = false;
                setSignedup("Nickname already taken");
            }
        }
        return check;
    }

    /**
     * Create a new User and add it to the User list
     * @param n nickname
     * @param e email
     * @param p password
     * @param a age
     * @param s sex
     * @param w weight
     */
    private static void CreateUser(String n, String e, String p, Integer a, String s, Double w){
        User newuser = new User(n,e,w,a,p,s);
        users.add(newuser);
        WelcomePage.setUsers(users);
    }

    /**
     * @return signedup : this String contain the information to display in case of error in SignUp_Control
     */
    public static String getSignedup() {
        return signedup;
    }

    /**
     * @param signedup
     */
    public static void setSignedup(String signedup) {
        SignUp.signedup = signedup;
    }
}
