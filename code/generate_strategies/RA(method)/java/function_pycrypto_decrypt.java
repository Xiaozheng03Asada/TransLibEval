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
            // 注册BouncyCastle提供者
            Security.addProvider(new BouncyCastleProvider());

            // 保证key长度为32字节
            key = String.format("%-32s", key).substring(0, 32);

            if ("encrypt".equals(mode)) {
                // 生成随机IV
                byte[] iv = new byte[16];
                SecureRandom random = new SecureRandom();
                random.nextBytes(iv);

                // 初始化加密器
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

                // 加密
                byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

                // 组合IV和密文并Base64编码
                byte[] combined = new byte[iv.length + encrypted.length];
                System.arraycopy(iv, 0, combined, 0, iv.length);
                System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

                return new String(Base64.encode(combined), StandardCharsets.UTF_8);

            } else if ("decrypt".equals(mode)) {
                // Base64解码
                byte[] combined = Base64.decode(text);
                if (combined.length < 16) {
                    throw new Exception("Invalid encrypted text");
                }

                // 分离IV和密文
                byte[] iv = new byte[16];
                byte[] encrypted = new byte[combined.length - 16];
                System.arraycopy(combined, 0, iv, 0, 16);
                System.arraycopy(combined, 16, encrypted, 0, encrypted.length);

                // 初始化解密器
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                // 解密
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