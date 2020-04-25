package info;

/**
 * CustomDrink is for the moment the only object containing
 * the information of a drink.
 *
 * @author GriffinBabe
 */
public class CustomDrink {
    private String name;

    // In centiliters
    private float volume;
    private float alcoholPercent;

    public CustomDrink(String name, float volume, float alcoholPercent) {
        this.name = name;
        this.volume = volume;
        this.alcoholPercent = alcoholPercent;
    }

    public String getName() {
        return name;
    }

    public float getVolume() {
        return volume;
    }

    public float getAlcoholPercent() {
        return alcoholPercent;
    }
}
