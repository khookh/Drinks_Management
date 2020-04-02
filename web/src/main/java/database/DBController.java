package database;

/**
 * DBController is a singleton patterned class that
 * communicates to the sql database.
 *
 * @author GriffinBabe
 */
public class DBController {

    /** Singleton {@link DBController} instace. */
    private static DBController instance = null;


    /**
     * {@link DBController} constructor.
     * Private as we use singleton pattern.
     */
    private DBController() {
    }

    /**
     * Singleton design pattern. Gets the instance of
     * {@link DBController} to access the SQL database.
     * @return
     */
    public static DBController getInstance() {
        if (DBController.instance == null) {
            DBController.instance = new DBController();
        }
        return DBController.instance;
    }

}
