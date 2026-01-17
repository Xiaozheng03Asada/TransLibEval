package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_pycrypto_aes {

    private String key = "mysecretpassword";
    private String plaintext = "This is a test message for AESCipher.";
    private function_pycrypto_aes cipher;

    private String encrypted;
    private String decrypted;

    @BeforeEach
    void setUp() {
        cipher = new function_pycrypto_aes();
    }

    @Test
    void test_encrypt_decrypt_basic_text() {
        String plaintext = "Hello, World!@#$%^&*()_+-=~1234567890";
        encrypted = cipher.process(plaintext, key, "encrypt");
        decrypted = cipher.process(encrypted, key, "decrypt");
        assertEquals(plaintext, decrypted);
    }

    @Test
    void test_decrypt_with_wrong_key() {
        String plaintext = "This is a test message.";
        encrypted = cipher.process(plaintext, key, "encrypt");
        String wrongKey = "wrongpassword";
        assertThrows(IllegalArgumentException.class, () -> cipher.process(encrypted, wrongKey, "decrypt"));
    }

    @Test
    void test_decrypt_partial_ciphertext() {
        String plaintext = "Partial decryption test";
        encrypted = cipher.process(plaintext, key, "encrypt");
        String partialEncrypted = encrypted.substring(0, encrypted.length() / 2);
        assertThrows(IllegalArgumentException.class, () -> cipher.process(partialEncrypted, key, "decrypt"));
    }

    @Test
    void test_decrypt_tampered_ciphertext() {
        String plaintext = "Tamper test";
        encrypted = cipher.process(plaintext, key, "encrypt");
        String tamperedEncrypted = encrypted.substring(0, encrypted.length() - 1) + "X";
        assertThrows(IllegalArgumentException.class, () -> cipher.process(tamperedEncrypted, key, "decrypt"));
    }

    @Test
    void test_aes_128_192_256_bit_key() {
        String plaintext = this.plaintext;

        String key128 = "thisis128bitkey";
        encrypted = cipher.process(plaintext, key128, "encrypt");
        decrypted = cipher.process(encrypted, key128, "decrypt");
        assertEquals(plaintext, decrypted);

        String key192 = "thisis192bitkey______";
        encrypted = cipher.process(plaintext, key192, "encrypt");
        decrypted = cipher.process(encrypted, key192, "decrypt");
        assertEquals(plaintext, decrypted);

        String key256 = "thisis256bitkey______________";
        encrypted = cipher.process(plaintext, key256, "encrypt");
        decrypted = cipher.process(encrypted, key256, "decrypt");
        assertEquals(plaintext, decrypted);
    }
}
