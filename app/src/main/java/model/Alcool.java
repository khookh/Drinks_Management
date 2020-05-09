package model;

import java.io.Serializable;

public class Alcool implements Serializable {
        private Double UA;
    private String Name;
    private Double Volume; //in cL
    private Double Percentage; // in %
    private Double AlQt; // in cL, quantity of alcohol in the drink

public Alcool(){}
public Alcool(String name, Double volume, Double percentage){
        Name = name;
        Volume = volume;
        Percentage = percentage;
        AlQt = Percentage*Volume; //qt d'éthanol dans la boisson
        UA = AlQt / 1.25 ; // une unité d'alcool correspond à 1.25cL d'alcool pur (OMS), UA est l'équivalent en unité d'alcool de la boisson
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