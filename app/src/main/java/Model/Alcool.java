package Model;

public class Alcool {
    private String Name;
    private Double Volume;
    private Double Percentage;
    public Alcool(String name, Double volume, Double percentage) {
        Name = name;
        Volume = volume;
        Percentage = percentage;
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
