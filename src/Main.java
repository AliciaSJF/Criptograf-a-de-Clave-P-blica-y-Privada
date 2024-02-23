import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {
        // Generar el par de claves pública y privada
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair pair = keyPairGen.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        // Inicializar un mensaje
        String originalMessage = "Este es un mensaje secreto";

        // Encriptación
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(originalMessage.getBytes());
        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

        // Desencriptación
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessageBytes = decryptCipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        String decryptedMessage = new String(decryptedMessageBytes);

        // Resultados
        System.out.println("Mensaje original: " + originalMessage);
        System.out.println("Mensaje encriptado: " + encryptedMessage);
        System.out.println("Mensaje desencriptado: " + decryptedMessage);
    }
}