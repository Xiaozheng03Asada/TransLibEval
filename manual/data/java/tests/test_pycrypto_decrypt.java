package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_pycrypto_decrypt {
    private String plaintext;
    private String key;
    private function_pycrypto_decrypt cipher;

    @BeforeEach
    void setUp() {
        plaintext = "This is a test message for AES.";
        key = "thisis256bitkey______________";
        cipher = new function_pycrypto_decrypt();
    }

    @Test
    void testEncryptDecryptValidText() {
        String encrypted = cipher.process(key, plaintext, "encrypt");
        String decrypted = cipher.process(key, encrypted, "decrypt");
        assertEquals(plaintext, decrypted);
    }

    @Test
    void testDecryptInvalidEncryptedText() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            cipher.process(key, "invalidtext", "decrypt");
        });
    }

    @Test
    void testEncryptDecryptEmptyString() {
        String encryptedEmpty = cipher.process(key, "", "encrypt");
        String decryptedEmpty = cipher.process(key, encryptedEmpty, "decrypt");
        assertEquals("", decryptedEmpty);
    }

    @Test
    void testDecryptAfterDoubleEncryption() {
        String encryptedOnce = cipher.process(key, plaintext, "encrypt");
        String decryptedOnce = cipher.process(key, encryptedOnce, "decrypt");
        assertEquals(plaintext, decryptedOnce);

        String encryptedTwice = cipher.process(key, decryptedOnce, "encrypt");
        String decryptedTwice = cipher.process(key, encryptedTwice, "decrypt");
        assertEquals(plaintext, decryptedTwice);
    }

    @Test
    void testDecryptWithAlteredCiphertext() {
        String encrypted = cipher.process(key, plaintext, "encrypt");
        String alteredEncrypted = encrypted.substring(0, encrypted.length() - 1) + "x";
        assertThrows(RuntimeException.class, () -> {
            cipher.process(key, alteredEncrypted, "decrypt");
        });
    }
}