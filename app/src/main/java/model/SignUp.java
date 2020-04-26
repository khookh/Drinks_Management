package model;

import controller.SignUp_Control;

import java.util.ArrayList;
public class SignUp {
    //todo: remove static
    private static String signedup = "error";
    private static Initialize init;
    private static ArrayList<User> users = getInit().getUsers();

    /**
     * SignUp instance; verify the information and proceed to sign up
     * @param nickname
     * @param email
     * @param password
     * @param age
     * @param sex
     * @param weight
     * @param init
     */
    public SignUp(String nickname, String email, String password, Integer age, String sex, Double weight, Initialize init){
        if(checkUser(email,nickname)){
            createUser(nickname,email,password,age,sex,weight);
            setSignedup(SignUp_Control.getErrormessage4());
        }
    }

    /**
     * @return boolean check ; if the user already exist or not
     */
    private boolean checkUser(String e, String n) {
        boolean check = true;
        //
        //
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
    private void createUser(String n, String e, String p, Integer a, String s, Double w){
        User newuser = new User(n,e,w,a,p,s);

        //
        //users.add(newuser);
        //WelcomePage.setUsers(users);
        //
    }

    /**
     * @return signedup : this String contain the information to display in case of error in SignUp_Control
     */
    public String getSignedup() {
        return signedup;
    }

    public void setSignedup(String signedup) {
        SignUp.signedup = signedup;
    }
    public static Initialize getInit() {
        return init;
    }

    public void setInit(Initialize init) {
        this.init = init;
    }

}
