package com.example;

import org.apache.commons.codec.digest.DigestUtils;
import java.nio.charset.StandardCharsets;

public class SHA256Hasher {
    // 所有功能均在这个唯一的方法中实现
    public String hash_text(String text) {
        // 使用Apache Commons Codec计算SHA256哈希
        return DigestUtils.sha256Hex(text.getBytes(StandardCharsets.UTF_8));
    }
}