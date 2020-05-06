package util;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Utility collection of string generators. Such as
 * {@link #generateToken(int)}.
 *
 * @author GriffinBabe
 */
public class StringGenerators {

    /**
     * Returns a new random token with the specified lenght.
     * All characters are from the {@link StandardCharsets#US_ASCII}
     * character set.
     *
     * @param length
     * @return
     */
    public static String generateToken(int length) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Creates a tuple of multiple ids to find in a string.
     * The created string is supposed to be used to do a multiple
     * entry query.
     *
     * @param ids, the ids to fill
     * @return the list on string format
     */
    public static String buildIdList(int[] ids) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            builder.append(i+",");
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }
}
