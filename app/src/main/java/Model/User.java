package Model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class User {
    private String Name;
    private String Email;
    private Double Weight;
    private Integer Age;
    private String Password;
    private HashMap<LocalDateTime,Alcool> Consumption;

    public User(String name, String email, Double weight, Integer age, String password){
        Name = name;
        Email = email;
        Weight = weight;
        Age = age;
        Password = password;
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

    public HashMap<LocalDateTime, Alcool> getConsumption() {
        return Consumption;
    }


}
