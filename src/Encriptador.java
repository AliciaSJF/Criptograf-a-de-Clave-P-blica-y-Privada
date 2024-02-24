import java.security.*;
import javax.crypto.*;
import java.util.Base64;
/*
Clase que proporciona métodos para encriptar y desencriptar
 */
public class Encriptador {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    /**
     * Constructor que inicializa las claves pública y privada.
     * @throws Exception si ocurre un error durante la inicialización de las claves.
     */

    public Encriptador() throws Exception {
        //Generar par de claves RSA de 2048 bits
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        KeyPair keyPair = keyGenerator.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }
    /**
     * Método para obtener la clave pública.
     * @return la clave pública.
     */
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    /**
     * Método para encriptar un mensaje con la clave pública proporcionada.
     * @param message el mensaje a encriptar.
     * @param publicKey la clave pública con la que se encriptará el mensaje.
     * @return el mensaje encriptado como una cadena Base64.
     * @throws Exception si ocurre un error durante el proceso de encriptación.
     */
    public String encriptar(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    /**
     * Método para desencriptar un mensaje encriptado.
     * @param encryptedMessage el mensaje encriptado como una cadena Base64.
     * @return el mensaje desencriptado.
     * @throws Exception si ocurre un error durante el proceso de desencriptación.
     */
    public String desencriptar(String encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedBytes);
    }

    /**
     * Método principal utilizado para demostrar el funcionamiento de la clase.
     * @param args argumentos de la línea de comandos (no se utilizan en este ejemplo).
     */
    public static void main(String[] args) {
        try {
            // Crear instancias para Alicia y Alvaro
            Encriptador alicia = new Encriptador();
            Encriptador alvaro = new Encriptador();

            // Simular mensaje de Alicia a Alvaro
            String mensajeAlicia = "Esto es un mensaje secreto";
            String mensajeAliciaEncriptado = alicia.encriptar(mensajeAlicia, alvaro.getPublicKey());
            System.out.println("Alicia envía: " + mensajeAliciaEncriptado);

            // Alvaro desencripta el mensaje de Alicia
            String mensajeAliciaDesencriptado = alvaro.desencriptar(mensajeAliciaEncriptado);
            System.out.println("Alvaro recibe y desencripta: " + mensajeAliciaDesencriptado);

            // Simular respuesta de Alvaro a Alicia
            String respuestaAlvaro = "Mensaje secreto recibido";
            String respuestaAlvaroEncriptado = alvaro.encriptar(respuestaAlvaro, alicia.getPublicKey());
            System.out.println("Alvaro responde: " + respuestaAlvaroEncriptado);

            // Alicia desencripta la respuesta de Alvaro
            String respuestaAlvaroDesencriptada = alicia.desencriptar(respuestaAlvaroEncriptado);
            System.out.println("Alicia recibe y desencripta: " + respuestaAlvaroDesencriptada);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}