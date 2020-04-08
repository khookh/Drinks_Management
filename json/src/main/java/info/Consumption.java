package info;

import java.sql.Date;

/**
 * Consumption data. A consumption represents a user that drinks
 * a certain {@link CustomDrink} at a certain {@link Date}.
 *
 * @author GriffinBabe
 */
public class Consumption {

    private CustomDrink drink;

    private Date consumptionTime;

    public Consumption(CustomDrink drink, Date date) {
        this.drink = drink;
        this.consumptionTime = date;
    }

    public CustomDrink getDrink() {
        return drink;
    }

    public Date getConsumptionTime() {
        return consumptionTime;
    }
}
