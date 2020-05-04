package model;

public class SignUp {
    String errormessage4 ;


    //todo: remove static
    private static String signedup = "error";
    private static Initialize init;

    /**
     * SignUp instance; verify the information and proceed to sign up
     * @param nickname
     * @param email
     * @param password
     * @param age
     * @param sex
     * @param weight
     * @param js
     */
    public SignUp(String nickname, String email, String password, Integer age, String sex, Double weight, JSONHandler js){
        if(checkUser(email,nickname)){
            setSignedup(errormessage4);
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
     * @return signedup : this String contain the information to display in case of error in SignUp_Control
     */
    public String getSignedup() {
        return signedup;
    }

    public void setSignedup(String signedup) {
        SignUp.signedup = signedup;
    }

    public void setErrormessage4(String errormessage4) {
        this.errormessage4 = errormessage4;
    }


}
