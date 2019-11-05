package ua.training.util.secure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class SecurePasswordMD5Test {

    private File passwords;
    private File hashedPaswords;

    @Before
    public void init() {
        ClassLoader classLoader = getClass().getClassLoader();
        passwords = new File(classLoader.getResource("passwords").getFile());
        hashedPaswords = new File(classLoader.getResource("hashed_passwords").getFile());
    }

    @Test
    public void getSecurePassword() throws Exception {
        try (Scanner scannerPass = new Scanner(passwords); Scanner scannerHash = new Scanner(hashedPaswords)) {
            while (scannerPass.hasNextLine() && scannerHash.hasNextLine()) {
                String password = scannerPass.nextLine();
                String hash = scannerHash.nextLine();
                assertEquals(hash, SecurePasswordMD5.getSecurePassword(password));
            }
            scannerPass.close();
            scannerHash.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verifyPassword() throws Exception {
        try (Scanner scannerPass = new Scanner(passwords); Scanner scannerHash = new Scanner(hashedPaswords)) {
            while (scannerPass.hasNextLine() && scannerHash.hasNextLine()) {
                String password = scannerPass.nextLine();
                String hash = scannerHash.nextLine();
                assertTrue(SecurePasswordMD5.verifyPassword(password,hash));
            }
            scannerPass.close();
            scannerHash.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}