package model;

import controller.WelcomePage;

import java.util.ArrayList;

public class SignIn {
    //todo: remove static

    private String signedin = "error";

    /**
     * SignIn instance; verify the information and proceed to sign in
     * @param nickname
     * @param password
     */
    public SignIn(String nickname, String password){
        LoginPacket packet = new LoginPacket(nickname, password);
        String response = WelcomePage.getHm().sendPost("", WriteJSON.writePacket(packet)); //bug merge
        setSignedin(response);

    }

    /**
     * @return signedin : this string contain the information to display in case of error
     */
    public String getSignedin() {
        return signedin;
    }

    public void setSignedin(String signedin) {
        this.signedin = signedin;
    }
}