package aix.study.orix.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author Sujith T
 *
 * <!In God We Trust>
 */
public class DataCrypto {

    private static final String ALGORITHM_RSA = "RSA";
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    /**
     * Create Public Private Keys
     * 
     * @throws aix.study.orix.util.UtilException
     */    
    public static void createKeys() throws UtilException {
        
        try {
            
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
            
            try ( //creating public key
                    FileOutputStream fpubkey = new FileOutputStream(new File(Config.get("auth.public.key")))) {
                fpubkey.write(publicKey.getEncoded());
                fpubkey.flush();
            }
            
            try ( //creating private key
                    FileOutputStream fprikey = new FileOutputStream(new File(Config.get("auth.private.key")))) {
                fprikey.write(privateKey.getEncoded());
                fprikey.flush();
            }
            
        } catch(IOException | NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
    }
    
    /**
     * Encrypts given string
     * 
     * @param data
     * @return String
     * @throws aix.study.orix.util.UtilException
     */     
    public static String encrypt(String data) throws UtilException {

        try {
            
            if(privateKey == null) {
                byte[] keyBytes = Files.readAllBytes(new File(Config.get("auth.private.key")).toPath());
                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory kf = KeyFactory.getInstance(ALGORITHM_RSA);
                privateKey = kf.generatePrivate(spec);
            }
            
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            return Base64.encodeBase64String(cipher.doFinal(data.getBytes("UTF-8")));
            
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new UtilException("Encryption Failed: ", e);
        }
    }
    
    /**
     * Decrypts given string
     * 
     * @param data
     * @return String
     * @throws aix.study.orix.util.UtilException
     */    
    public static String decrypt(String data) throws UtilException {
        
        try {
            if(publicKey == null) {
                byte[] keyBytes = Files.readAllBytes(new File(Config.get("auth.public.key")).toPath());
                X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
                KeyFactory kf = KeyFactory.getInstance(ALGORITHM_RSA);
                publicKey = kf.generatePublic(spec);
            }
            
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            
            return new String(cipher.doFinal(Base64.decodeBase64(data)), "UTF-8");
            
        } catch(IOException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new UtilException("Decryption Failed: ", e);
        }
    }
}
