package models;

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
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class RSA {
	private PrivateKey priKey;
    private PublicKey pubKey;
    public static String privateKey;
    public static String publicKey;

    public RSA(int i) {
    }
    public RSA() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair pair = keyGen.generateKeyPair();
        this.priKey = pair.getPrivate();
        this.pubKey = pair.getPublic();
        RSA.privateKey = Base64.getEncoder().encodeToString(this.priKey.getEncoded());
        RSA.publicKey = Base64.getEncoder().encodeToString(this.pubKey.getEncoded());
    }
    public PrivateKey getPrivateKey() {
        return priKey;
    }

    public PublicKey getPublicKey() {
        return pubKey;
    }
    
    public PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }
    
    public PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }
    
    public byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
    	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    	cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
    	return cipher.doFinal(data.getBytes());
    }
    
    public String encryptRSA(String data, String publicKey) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
    	return Base64.getEncoder().encodeToString(new RSA(0).encrypt(data, publicKey));
    }
    
    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }
    
    public static String decryptRSA(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), new RSA(0).getPrivateKey(base64PrivateKey));
    }
    
    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
        	RSA keyPairGenerator = new RSA();
        	System.out.println("publicKey : "+publicKey);
            String encryptedString = new RSA(0).encryptRSA("javax.crypto.spec.SecretKeySpec@fffe871f", publicKey);
            System.out.println("Ma hoa : "+encryptedString);
            String decryptedString = keyPairGenerator.decryptRSA(encryptedString, privateKey);
            System.out.println("privateKey : "+privateKey);
            System.out.println("Giai ma : "+decryptedString);
            SecretKey key = new ExecuteED().convertStringToSecretKey(decryptedString);
            System.out.println(key);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    }
}
