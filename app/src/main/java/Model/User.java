package Model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class User {
    private String Name;
    private String Email;
    private Double Weight;
    private Integer Age;
    private String Password;
    private String Sex;
    private HashMap<LocalDateTime,Alcool> Consumption;
    private Double AlcoolRate = 0.0; // en g/L dans le sang

    public User(String name, String email, Double weight, Integer age, String password, String sex){
        Name = name;
        Email = email;
        Weight = weight;
        Age = age;
        Password = password;
        Sex = sex;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public Double getWeight() {
        return Weight;
    }

    public Integer getAge() {
        return Age;
    }

    public String getPassword() {
        return Password;
    }

    public String getSex() { return Sex; }

    public void setSex(String sex) { Sex = sex; }

    public Double getAlcoolRate() {
        return AlcoolRate;
    }

    public void setAlcoolRate(Double alcoolRate) {
        AlcoolRate = alcoolRate;
    }

    public HashMap<LocalDateTime, Alcool> getConsumption() {
        return Consumption;
    }

    public void setConsumption(HashMap<LocalDateTime, Alcool> consumption) {
        Consumption = consumption;
    }


}
