package by.academy.cinema.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class HashPassword {

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String codeSalt(byte[] salt) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(salt);
    }

    public static String getSaltHash(String pass, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashArray = factory.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            return enc.encodeToString(hashArray);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decodeSalt(String salt) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(salt);
    }

}