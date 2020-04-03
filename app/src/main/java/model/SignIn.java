package model;

import controller.WelcomePage;

import java.util.ArrayList;

public class SignIn {


    private static String signedin = "error";

    /**
     * SignIn instance; verify the information and proceed to sign in
     * @param nickname
     * @param password
     */
    public static void signIn(String nickname, String password){
        checkUser(password,nickname);
        //
    }

    /**
     * Check if the user exists
     * @param pw
     * @param name
     */
    private static void checkUser(String pw, String name) {

        ArrayList<User> users = WelcomePage.getUsers();
        //check dans le stockage, arraylist sol. temporaire pour test
        setSignedin("User doesn't exist");
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getName().equals(name)){
                setSignedin("Wrong Password");
                if(users.get(i).getPassword().equals(pw)){
                    setSignedin("Signed In");
                    WelcomePage.setActual_user(users.get(i));
                }
            }
        }
    }

    /**
     * @return signedin : this string contain the information to display in case of error
     */
    public static String getSignedin() {
        return signedin;
    }

    public static void setSignedin(String signedin) {
        SignIn.signedin = signedin;
    }
}