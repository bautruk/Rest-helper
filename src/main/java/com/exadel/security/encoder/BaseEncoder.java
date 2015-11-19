package com.exadel.security.encoder;

import org.apache.commons.codec.binary.Base64;
import sun.security.pkcs.EncodingException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;

/**
 * Created by drovdo on 11/16/2015.
 */
public class BaseEncoder implements Encoder{
    private static final String UTF_8 = "UTF-8";
    private final String SPEC_ALGORITHM;
    private final String ALGORITHM;
    private final Cipher CIPHER;

    protected BaseEncoder(String algorithm, String specAlgorithm) {
        this.SPEC_ALGORITHM = specAlgorithm;
        this.ALGORITHM = algorithm;
        try {
            CIPHER = Cipher.getInstance(algorithm);
        } catch (Exception e) {
            System.out.println("Error init paired cipher");
            throw new RuntimeException("Can't init paired cipher");
        }
    }


    @Override
    public byte[] generateKey() throws EncodingException{
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(SPEC_ALGORITHM);
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (Exception e){
            System.out.println("Error generate key");
            throw new EncodingException("Can't generate key");
        }
    }

    @Override
    public byte[] encode(byte[] data, PublicKey key) throws EncodingException{
        try {
            CIPHER.init(Cipher.ENCRYPT_MODE, key);
            return CIPHER.doFinal(data);
        } catch (Exception e) {
            System.out.println("Error encrypt data");
            throw new EncodingException("Error encrypting data");
        }
    }

    @Override
    public String encodeToBase64(byte[] data, PublicKey key) throws EncodingException{
        return byteToBase64(encode(data, key));
    }

    @Override
    public String encodeToBase64(String data, PublicKey key) throws EncodingException{
        try {
            byte[] dataBytes = data.getBytes(UTF_8);
            return byteToBase64(encode(dataBytes, key));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error getting UTF-8 bytes for BaseEncoder");
            throw new EncodingException("Error getting UTF-8 bytes");
        }
    }

    @Override
    public byte[] encodeWithZeroVector(byte[] data, byte[] key) {
        byte[] alternativeIV ={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        IvParameterSpec ips = new IvParameterSpec(alternativeIV);
        SecretKeySpec aesKey = new SecretKeySpec(key, SPEC_ALGORITHM);
        try {
            CIPHER.init(Cipher.ENCRYPT_MODE, aesKey, ips);
            return CIPHER.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data");
        }
    }

    private String byteToBase64(byte[] data){
        return new String(Base64.encodeBase64(data));
    }
}
