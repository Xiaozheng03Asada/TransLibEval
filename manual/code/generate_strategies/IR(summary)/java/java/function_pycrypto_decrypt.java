package com.example;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class AESCipher {
    public String process(String key, String text, String mode) {
        try {
            
            Security.addProvider(new BouncyCastleProvider());

            
            key = String.format("%-32s", key).substring(0, 32);

            if ("encrypt".equals(mode)) {
                
                byte[] iv = new byte[16];
                SecureRandom random = new SecureRandom();
                random.nextBytes(iv);

                
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

                
                byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

                
                byte[] combined = new byte[iv.length + encrypted.length];
                System.arraycopy(iv, 0, combined, 0, iv.length);
                System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

                return new String(Base64.encode(combined), StandardCharsets.UTF_8);

            } else if ("decrypt".equals(mode)) {
                
                byte[] combined = Base64.decode(text);
                if (combined.length < 16) {
                    throw new Exception("Invalid encrypted text");
                }

                
                byte[] iv = new byte[16];
                byte[] encrypted = new byte[combined.length - 16];
                System.arraycopy(combined, 0, iv, 0, 16);
                System.arraycopy(combined, 16, encrypted, 0, encrypted.length);

                
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                
                byte[] decrypted = cipher.doFinal(encrypted);
                return new String(decrypted, StandardCharsets.UTF_8);
            } else {
                throw new IllegalArgumentException("Mode must be 'encrypt' or 'decrypt'");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}