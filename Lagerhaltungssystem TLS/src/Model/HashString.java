package Model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Its Simply is a Hashed String
 */
public class HashString implements Serializable {

    private final String hash;

    /**
     * Creates a new HashString
     *
     * @param str the String to Hash
     */
    public HashString(String str) {
        this.hash = toHash(str);
    }

    /**
     * Hash the String
     *
     * @param str the String to Hash
     * @return the  HashValue of the given String
     */
    private String toHash(String str) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.exit(0x966);
        }
        md.digest(str.getBytes());

        String hash = new BigInteger(1, md.digest(str.getBytes())).toString(16);

        while (hash.length() < 32)
            hash = "0" + hash;

        return hash;
    }

    /**
     * Get the HashValue as String
     *
     * @return the Hash value as String
     */
    public String get() {
        return this.hash;
    }
}
