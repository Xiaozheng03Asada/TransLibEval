package com.example;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import java.io.StringWriter;
import java.util.Base64;

public class RSACrypto {

    public String rsa_process(String text, String mode, int key_size, String public_key_str, String private_key_str) {
        try {
            if (mode.equals("generate_keypair")) {
                // Generate key pair
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(key_size);
                KeyPair pair = keyGen.generateKeyPair();
                PrivateKey privateKey = pair.getPrivate();
                PublicKey publicKey = pair.getPublic();

                // Convert keys to PEM format using Bouncy Castle's PemWriter
                StringWriter swPrivate = new StringWriter();
                PemWriter pemWriterPrivate = new PemWriter(swPrivate);
                pemWriterPrivate.writeObject(new PemObject("RSA PRIVATE KEY", privateKey.getEncoded()));
                pemWriterPrivate.flush();
                pemWriterPrivate.close();
                StringWriter swPublic = new StringWriter();
                PemWriter pemWriterPublic = new PemWriter(swPublic);
                pemWriterPublic.writeObject(new PemObject("RSA PUBLIC KEY", publicKey.getEncoded()));
                pemWriterPublic.flush();
                pemWriterPublic.close();
                // Return concatenated keys separated by "|||"
                return swPrivate.toString() + "|||" + swPublic.toString();
            } else if (mode.equals("encrypt")) {
                if (text == null || public_key_str == null) {
                    throw new IllegalArgumentException("Text and public key must be provided for encryption");
                }
                // Extract the public key content from PEM
                String publicKeyPEM = public_key_str.replace("-----BEGIN RSA PUBLIC KEY-----", "")
                        .replace("-----END RSA PUBLIC KEY-----", "")
                        .replaceAll("\\s", "");
                byte[] encodedPublicKey = Base64.getDecoder().decode(publicKeyPEM);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedPublicKey);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = keyFactory.generatePublic(keySpec);

                // Initialize Cipher for RSA/ECB/OAEP encryption
                Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);

                // Check if text length can be encrypted.
                int keyByteSize = ((RSAPublicKey) publicKey).getModulus().bitLength() / 8;
                if (text.getBytes("UTF-8").length > keyByteSize - 42) {
                    throw new IllegalArgumentException("Text too long for encryption");
                }

                byte[] cipherText = cipher.doFinal(text.getBytes("UTF-8"));
                return Base64.getEncoder().encodeToString(cipherText);
            } else if (mode.equals("decrypt")) {
                if (text == null || private_key_str == null) {
                    throw new IllegalArgumentException("Encrypted text and private key must be provided for decryption");
                }
                // Extract the private key content from PEM
                String privateKeyPEM = private_key_str.replace("-----BEGIN RSA PRIVATE KEY-----", "")
                        .replace("-----END RSA PRIVATE KEY-----", "")
                        .replaceAll("\\s", "");
                byte[] encodedPrivateKey = Base64.getDecoder().decode(privateKeyPEM);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

                // Initialize Cipher for RSA/ECB/OAEP decryption
                Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(text));
                return new String(decryptedBytes, "UTF-8");
            } else {
                throw new IllegalArgumentException("Mode must be 'encrypt', 'decrypt', or 'generate_keypair'");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}