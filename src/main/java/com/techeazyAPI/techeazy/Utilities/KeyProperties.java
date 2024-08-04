package com.techeazyAPI.techeazy.Utilities;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.stereotype.Component;

@Component
public class KeyProperties {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;


    public KeyProperties() {
        KeyPair keypair= KeyGenerator.generateKey();
        this.privateKey =   (RSAPrivateKey) keypair.getPrivate();
        this.publicKey =    (RSAPublicKey) keypair.getPublic();
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }




}
