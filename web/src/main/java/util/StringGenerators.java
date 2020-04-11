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
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.US_ASCII);
    }
}
