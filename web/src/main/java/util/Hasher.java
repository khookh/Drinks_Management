package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility collection of Sha256 hashing methods.
 *
 * @author GriffinBabe
 */
public class Hasher {

    public static String hashString(String toHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
            return new String(hashed);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(-1); // fatal error
            return null; // useless but compiler doesn't complains
        }
    }

    public static boolean verifyString(String toVerify, String hashed) {
        String toVerifyHashed = Hasher.hashString(toVerify);
        return (toVerifyHashed.equals(hashed));
    }
}
