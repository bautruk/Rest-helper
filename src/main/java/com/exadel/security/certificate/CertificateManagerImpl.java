package com.exadel.security.certificate;

import com.exadel.security.encoder.Encoder;
import sun.security.pkcs.EncodingException;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * Created by drovdo on 11/16/2015.
 */
public class CertificateManagerImpl implements CertificateManager{
    private Encoder rsaEncoder;

    private final X509Certificate ROOT_CERT;
    private final PrivateKey ROOT_PRIVATE_KEY;

    public CertificateManagerImpl(String pathToStore, String storePassword, String alias)
            throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {

        KeyStore keystore  = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load (new FileInputStream(pathToStore), storePassword.toCharArray());
        this.ROOT_PRIVATE_KEY = (PrivateKey) keystore.getKey(alias, storePassword.toCharArray());
        this.ROOT_CERT = (X509Certificate) keystore.getCertificate(alias);

        if (ROOT_CERT == null || ROOT_PRIVATE_KEY == null) {
            throw new IllegalArgumentException("Not all required parameters are predefined. Can't get ROOT_CERT or rootPrivate key from the store.");
        }
    }

    @Override
    public boolean validate(X509Certificate certificate) {
        try {
            certificate.checkValidity(new Date());
            certificate.verify(ROOT_CERT.getPublicKey());
            return true;
        } catch (Exception e) {
            System.out.println("Certificate validation failed.");
            return false;
        }
    }

    @Override
    public String encodeWIthCertificate(String originalValue) throws EncodingException {
        return rsaEncoder.encodeToBase64(originalValue, ROOT_CERT.getPublicKey());
    }

    @Override
    public String encodeWIthCertificate(byte[] originalValue) throws EncodingException {
        return rsaEncoder.encodeToBase64(originalValue, ROOT_CERT.getPublicKey());
    }

    public void setRsaEncoder(Encoder rsaEncoder) {
        this.rsaEncoder = rsaEncoder;
    }
}
