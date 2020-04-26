package model;

import network.HttpManager;

import java.util.ArrayList;

public class Initialize {
    private static User actual_user;
    public static HttpManager hm;
    public static ArrayList<User> users = new ArrayList();
    public Initialize(){
        //TEMPORARY FOR TEST
        User newuser = new User("Stefano","e",85.6,23,"123456","boi");
        newuser.setAlcoolRate(0.1);
        setActual_user(newuser);
        users.add(newuser);
        setUsers(users);

        HttpManager hm = new HttpManager("URL","API_URL");
        setHm(hm);

        //TEMPORARY FOR TEST
    }

    public User getActual_user() {
        return actual_user;
    }
    public void setActual_user(User actual_user) {
       this.actual_user = actual_user;
    }
    public HttpManager getHm() {
        return hm;
    }
    public void setHm(HttpManager hm) {
        this.hm = hm;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public  void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
