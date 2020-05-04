package model;

public class SignIn {
    //todo: remove static

    private  String signedin = "error";


    /**
     * SignIn instance; verify the information and proceed to sign in
     * @param nickname
     * @param password
     * @param jsonHandler
     */
    public SignIn(String nickname, String password, JSONHandler jsonHandler){
        setSignedin("Wrong Nickname");
        if(jsonHandler.doesUserExist(nickname)){
            setSignedin("Wrong Password");
            User tempUser = jsonHandler.getUser(nickname);
            if(tempUser.getPassword().equals(password)) {
                jsonHandler.setActiveUser(tempUser);}
                setSignedin("Signed In");
        }
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