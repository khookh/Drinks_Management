package model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String name;
    private String email;
    private Double weight;
    private Integer age;
    private String password;
    private String sex;
    private String lastdrink; //TEMPORARY
    private HashMap<String,Alcool> consumption = new HashMap<>();
    //ON PEUT CONVERTIR LE STRING TIME EN LOCAL DATE TIME AVEC LocalDateTime dateTime = LocalDateTime.parse(str, formatter); pour le formatter see session.java
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
    public String getLastdrink() {
        return lastdrink;
    }

    public void setLastdrink(String time) {
        lastdrink = time;
    }
    //TEMPORARY

    public ArrayList<Alcool> getCustomAlcool() {
        return customAlcool;
    }

    public void setCustomAlcool(ArrayList<Alcool> customAlcool) {
        this.customAlcool = customAlcool;
    }


}
