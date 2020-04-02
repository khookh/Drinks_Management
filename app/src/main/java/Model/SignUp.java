package Model;

public class SignUp {
    private static String signedup = "error";
    public static String SignUp(String nickname, String email, String password, Integer age, String sex, Double weight){
        if(CheckUser()){
            CreateUser(nickname,email,password,age,sex,weight);
            signedup = "Signed Up";
        }
        return signedup;
    }

    private static boolean CheckUser() {
        boolean ok = false;
        //if(jsonHandler.doesUserExist(nickname)) { }
        //check dans le stockage
        return ok;
    }

    private static void CreateUser(String n, String e, String p, Integer a, String s, Double w){
        User newuser = new User(n,e,w,a,p,s);
        //+ stock new user
    }
}
