package studentteacher.utils;

import java.security.KeyStore;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.io.FileInputStream;

public class EncryptUtils {

    private static KeyStore keyStore;

    static {
        try {
            // Load the keystore (use JKS format if your keystore is JKS)
            FileInputStream keystoreFile = new FileInputStream("C:/Users/tarun/Downloads/weather-master/weather-master/config/src/main/resources/config-service.keystore");
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(keystoreFile, "123456".toCharArray()); // Keystore password

            // Retrieve the secret key from the keystore
            SecretKey secretKey = (SecretKey) keyStore.getKey("config-service-key", "123456".toCharArray()); // Alias and password for the secret key
            System.out.println("Secret Key: " + secretKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Encrypt the plain text (e.g., database username or password)
    public static String encrypt(String plainText) throws Exception {
        // Retrieve the secret key from the keystore
        SecretKey secretKey = (SecretKey) keyStore.getKey("config-service-key", "123456".toCharArray()); // Alias and password for the secret key

        // Use AES for encryption (you can choose a different algorithm if needed)
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        // Return the encrypted data as a Base64-encoded string
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the encrypted text
    public static String decrypt(String encryptedText) throws Exception {
        // Retrieve the secret key from the keystore
        SecretKey secretKey = (SecretKey) keyStore.getKey("config-service-key", "123456".toCharArray()); // Alias and password for the secret key

        // Use AES for decryption (must match the encryption algorithm)
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

        // Return the decrypted text
        return new String(decryptedBytes);
    }
}
