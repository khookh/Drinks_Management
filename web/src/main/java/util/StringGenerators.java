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
}
