package com.exadel.security.encoder;

import sun.security.pkcs.EncodingException;

import java.security.PublicKey;

/**
 * Created by drovdo on 11/16/2015.
 */
public interface Encoder {
    byte[] generateKey() throws EncodingException;
    byte[] encode(byte[] data, PublicKey key) throws EncodingException;
    String encodeToBase64(byte[] data, PublicKey key) throws EncodingException;
    String encodeToBase64(String data, PublicKey key) throws EncodingException;
    byte[] encodeWithZeroVector(byte[] data, byte[] key);
}
