package model;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String name;
    private String email;
    private Double weight;
    private Integer age;
    private String password;
    private String sex;
    private static Pair<String, Alcool> lastdrink; //TEMPORARY
    private HashMap<String,Alcool> consumption = new HashMap<>();

    private ArrayList<Alcool> customAlcool = new ArrayList<>();
    private Double alcoolRate = 0.0; // en g/L dans le sang

    public User(){}
    public User(String name, String email, Double weight, Integer age, String password, String sex){
        this.name = name;
        this.email = email;
        this.weight = weight;
        this.age = age;
        this.password = password;
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() { return sex; }

    public void setSex(String sex) { this.sex = sex; }

    public Double getAlcoolRate() {
        return alcoolRate;
    }

    public void setAlcoolRate(Double alcoolRate) {
        this.alcoolRate = alcoolRate;
    }

    public HashMap<String, Alcool> getConsumption() {
        return consumption;
    }

    public void setConsumption(HashMap<String, Alcool> consumption) {
        this.consumption = consumption;
    }

    public void addConsumption(String time, Alcool new_alcohol){
        consumption.put(time,new_alcohol);
    }

    public void addCustom(Alcool alcool){customAlcool.add(alcool);}
    //TEMPORARY
    public static Pair<String, Alcool> getLastdrink() {
        return lastdrink;
    }

    public static void setLastdrink(String time , Alcool alcool) {
        lastdrink = new Pair<>(time,alcool);
    }
    //TEMPORARY

    public ArrayList<Alcool> getCustomAlcool() {
        return customAlcool;
    }

    public void setCustomAlcool(ArrayList<Alcool> customAlcool) {
        this.customAlcool = customAlcool;
    }


}
