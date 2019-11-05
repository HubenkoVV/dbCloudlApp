package ua.training.util.secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class to hashing passwords for greater protection
 */
public class SecurePasswordMD5 {

    /**
     * Method that makes hashed password into DB from registered password
     * @param passwordToHash password that user inputed
     * @return hashed password
     */
    public static String getSecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Method that check if inputed hashed password equals password from DB
     * @param password user's input
     * @param hashPassword password from DB
     * @return result of comparison
     */
    public static boolean verifyPassword(String password, String hashPassword){
        return getSecurePassword(password).equals(hashPassword);
    }
}
