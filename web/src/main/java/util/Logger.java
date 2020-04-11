package util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility object of logger, this will permit later to
 * save in a file logs.
 *
 * @author GriffinBabe
 */
public class Logger {

    private boolean print = false;

    private Map<Date, String> map = new HashMap<>();

    public Logger(boolean print) {
        this.print = print;
    }

    public void log(Object o, String logMsg) {
        String message = String.format("[%s] %s", o.getClass().getSimpleName(), logMsg);
        map.put(new Date(), message);
        if (this.print) {
            System.out.println(message);
        }
    }
}
