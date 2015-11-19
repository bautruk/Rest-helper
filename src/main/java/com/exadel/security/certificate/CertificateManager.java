package com.exadel.security.certificate;

import sun.security.pkcs.EncodingException;

import java.security.cert.X509Certificate;

/**
 * Created by drovdo on 11/16/2015.
 */
public interface CertificateManager {
    boolean validate(X509Certificate certificate);
    String encodeWIthCertificate(String originalValue) throws EncodingException;
    String encodeWIthCertificate(byte[] originalValue) throws EncodingException;
}
