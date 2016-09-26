package core;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jens on 26-Sep-16.
 */
public class Util {
    public static String getHashedAndSaltedPassword(String password, String salt) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            return HexBin.encode(messageDigest.digest((password + salt).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            // Should never happen
            e.printStackTrace();
        }
        // Should never happen
        return null;
    }
}
