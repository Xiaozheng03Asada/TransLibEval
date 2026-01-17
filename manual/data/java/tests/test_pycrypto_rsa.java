package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test_pycrypto_rsa {

    @Test
    public void test_rsa_encrypt_decrypt() {
        function_pycrypto_rsa rsaCrypto = new function_pycrypto_rsa();
        // Generate key pair; the returned value is "private_key_str|||public_key_str"
        String keyPair = rsaCrypto.rsa_process(null, "generate_keypair", 2048, null, null);
        String[] keys = keyPair.split("\\|\\|\\|");
        String privateKey = keys[0];
        String publicKey = keys[1];
        String plaintext = "This is a test message for rsa_process.";

        // Encrypt
        String encrypted = rsaCrypto.rsa_process(plaintext, "encrypt", 2048, publicKey, null);
        // Decrypt
        String decrypted = rsaCrypto.rsa_process(encrypted, "decrypt", 2048, null, privateKey);
        assertEquals(plaintext, decrypted);
    }

    @Test
    public void test_decrypt_with_wrong_key() {
        function_pycrypto_rsa rsaCrypto = new function_pycrypto_rsa();
        // Generate valid key pair
        String keyPair = rsaCrypto.rsa_process(null, "generate_keypair", 2048, null, null);
        String[] keys = keyPair.split("\\|\\|\\|");
        String privateKey = keys[0];
        String publicKey = keys[1];
        // Generate another key pair to use as the wrong key
        String wrongKeyPair = rsaCrypto.rsa_process(null, "generate_keypair", 2048, null, null);
        String[] wrongKeys = wrongKeyPair.split("\\|\\|\\|");
        String wrongPrivateKey = wrongKeys[0];

        String plaintext = "This is a test message for rsa_process.";
        String encrypted = rsaCrypto.rsa_process(plaintext, "encrypt", 2048, publicKey, null);
        // Decrypting using the wrong private key should throw an exception
        assertThrows(IllegalArgumentException.class, () -> {
            rsaCrypto.rsa_process(encrypted, "decrypt", 2048, null, wrongPrivateKey);
        });
    }

    @Test
    public void test_encrypt_large_text() {
        function_pycrypto_rsa rsaCrypto = new function_pycrypto_rsa();
        String keyPair = rsaCrypto.rsa_process(null, "generate_keypair", 2048, null, null);
        String[] keys = keyPair.split("\\|\\|\\|");
        String publicKey = keys[1];
        String longText = new String(new char[300]).replace("\0", "A");
        // Encrypting text that is too long should throw an exception
        assertThrows(IllegalArgumentException.class, () -> {
            rsaCrypto.rsa_process(longText, "encrypt", 2048, publicKey, null);
        });
    }

    @Test
    public void test_invalid_mode() {
        function_pycrypto_rsa rsaCrypto = new function_pycrypto_rsa();
        String keyPair = rsaCrypto.rsa_process(null, "generate_keypair", 2048, null, null);
        String[] keys = keyPair.split("\\|\\|\\|");
        String publicKey = keys[1];
        String plaintext = "This is a test message for rsa_process.";
        assertThrows(IllegalArgumentException.class, () -> {
            rsaCrypto.rsa_process(plaintext, "invalid_mode", 2048, publicKey, null);
        });
    }

    @Test
    public void test_rsa_key_pair_match() {
        function_pycrypto_rsa rsaCrypto = new function_pycrypto_rsa();
        String keyPair = rsaCrypto.rsa_process(null, "generate_keypair", 2048, null, null);
        String[] keys = keyPair.split("\\|\\|\\|");
        String privateKey = keys[0];
        String publicKey = keys[1];
        String plaintext = "This is a test message for rsa_process.";
        String encrypted = rsaCrypto.rsa_process(plaintext, "encrypt", 2048, publicKey, null);
        String decrypted = rsaCrypto.rsa_process(encrypted, "decrypt", 2048, null, privateKey);
        assertEquals(plaintext, decrypted);
    }
}