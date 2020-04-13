package model;

public class Alcool {
    private String Name;
    private Double Volume; //in cL
    private Double Percentage; // in %
    private Double AlQt; // in cL, quantity of alcohol in the drink
public Alcool(String name, Double volume, Double percentage) {
        Name = name;
        Volume = volume;
        Percentage = percentage;
        AlQt = Percentage*Volume;
        }

public String getName() {
        return Name;
        }

public void setName(String name) {
        Name = name;
        }

public Double getVolume() {
        return Volume;
        }

public void setVolume(Double volume) {
        Volume = volume;
        }

public Double getPercentage() {
        return Percentage;
        }

public void setPercentage(Double percentage) {
        Percentage = percentage;
        }
        }