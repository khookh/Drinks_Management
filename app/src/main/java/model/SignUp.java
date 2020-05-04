package model;

import java.util.regex.Pattern;

public class SignUp {
    private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
    //todo: remove static
    private static String signedup = "error";

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
        if(js.doesUserExist(nickname)){
            setSignedup("Nickname already taken");
        }
        else if(!emailPattern.matcher(email).matches()) {
            setSignedup("Wrong email format");
        }
        else{
            User newuser = new User(nickname,email,weight,age,password,sex);
            js.addUser(newuser);
            setSignedup("Signed Up");
        }
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




}
