
package models;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class ExecuteED {
    public static ArrayList<?> list = new ArrayList<>();

    public ExecuteED() {
    }
    
    public String next_ma(ArrayList <Integer> ma){
        int[] hashmap = new int[ma.size() + 107];
        for (int a : ma){
            if (a <= ma.size()){
                hashmap[a] = 1;
            }
        }
        int res = 1;
        while (hashmap[res] == 1){
            res++;
        }
        String nextma = Integer.toString(res);
        while (nextma.length() < 4){
            nextma = "0" + nextma;
        }
        return nextma;
    }
    public String next_maDeThi(ArrayList <String> ma){
        ArrayList <Integer> int_ma = new ArrayList <>();
        for (String i : ma){
            int_ma.add(Integer.parseInt(i.substring(2)));
        }
        return "DE" + next_ma(int_ma);
    }
    
    public static ArrayList<?> convertObjectToList(Object obj) {
        if (obj.getClass().isArray()) {
            list = (ArrayList<?>) Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }
    
    public String hashMD5(String passwordToHash) {
        String hashedPassword = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(passwordToHash.getBytes());
            byte[] bytes = md5.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return hashedPassword;
    }
    
    public SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }
    
    public IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    
    public String encrypt(String algorithm, String input, SecretKey key,
        IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
        InvalidAlgorithmParameterException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }
    
    public String decrypt(String algorithm, String cipherText, SecretKey key,
        IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
        InvalidAlgorithmParameterException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
            .decode(cipherText));
        return new String(plainText);
    }
    
    public SealedObject encryptObject(String algorithm, Serializable object,
        SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException,
        NoSuchAlgorithmException, InvalidAlgorithmParameterException, 
        InvalidKeyException, IOException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        SealedObject sealedObject = new SealedObject(object, cipher);
        return sealedObject;
    }
    
    public Serializable decryptObject(String algorithm, SealedObject sealedObject,
        SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException,
        NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
        ClassNotFoundException, BadPaddingException, IllegalBlockSizeException,
        IOException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        Serializable unsealObject = (Serializable) sealedObject.getObject(cipher);
        return unsealObject;
    }
}