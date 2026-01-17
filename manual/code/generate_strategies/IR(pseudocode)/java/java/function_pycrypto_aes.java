package com.example;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.security.Security;

public class AESCipher {

    public String process(String text, String key, String mode) {
        try {
            Security.addProvider(new BouncyCastleProvider());

            key = String.format("%-32s", key).substring(0, 32);
            int blockSize = 16;

            class Padding {
                String pad(String text) {
                    int paddingLength = blockSize - (text.length() % blockSize);
                    StringBuilder paddedText = new StringBuilder(text);
                    for (int i = 0; i < paddingLength; i++) {
                        paddedText.append((char) paddingLength);
                    }
                    return paddedText.toString();
                }

                String unpad(String text) {
                    int paddingLength = text.charAt(text.length() - 1);
                    return text.substring(0, text.length() - paddingLength);
                }
            }

            Padding padding = new Padding();

            if (mode.equals("encrypt")) {
                SecureRandom random = new SecureRandom();
                byte[] iv = new byte[blockSize];
                random.nextBytes(iv);
                IvParameterSpec ivSpec = new IvParameterSpec(iv);

                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

                byte[] cipherTextBytes = cipher.doFinal(padding.pad(text).getBytes(StandardCharsets.UTF_8));
                byte[] combined = new byte[iv.length + cipherTextBytes.length];
                System.arraycopy(iv, 0, combined, 0, iv.length);
                System.arraycopy(cipherTextBytes, 0, combined, iv.length, cipherTextBytes.length);

                return Base64.getEncoder().encodeToString(combined);

            } else if (mode.equals("decrypt")) {
                byte[] textBytes = Base64.getDecoder().decode(text);
                byte[] iv = new byte[blockSize];
                System.arraycopy(textBytes, 0, iv, 0, blockSize);
                IvParameterSpec ivSpec = new IvParameterSpec(iv);

                byte[] cipherTextBytes = new byte[textBytes.length - blockSize];
                System.arraycopy(textBytes, blockSize, cipherTextBytes, 0, cipherTextBytes.length);

                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

                byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);
                return padding.unpad(new String(plainTextBytes, StandardCharsets.UTF_8));

            } else {
                throw new IllegalArgumentException("Mode must be 'encrypt' or 'decrypt'");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}