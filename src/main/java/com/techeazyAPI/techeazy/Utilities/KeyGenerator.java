package com.techeazyAPI.techeazy.Utilities;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGenerator {

    public static KeyPair generateKey()  {
        KeyPair key;
        try {
            KeyPairGenerator generator= KeyPairGenerator.getInstance("RSA") ;
            generator.initialize(2048);
            key= generator.generateKeyPair();
        }catch (Exception e) {
            throw new IllegalStateException();
        }
        return key;
    }

}