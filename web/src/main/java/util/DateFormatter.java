package util;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Date formatting method tools.
 * Those methods are used to convert
 * {@link java.util.Date} into a {@link String}
 * and the other way around.
 *
 * @author GriffinBabe
 */
public class DateFormatter {

    private static SimpleDateFormat DATE_FORMATTER
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date fromString(String dateString) throws ParseException {
        return DATE_FORMATTER.parse(dateString);
    }

    public static String toString(Date date) throws ParseException {
        return DATE_FORMATTER.format(date);
    }

}
