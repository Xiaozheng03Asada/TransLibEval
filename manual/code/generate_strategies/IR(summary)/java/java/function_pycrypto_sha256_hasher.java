package com.example;

import org.apache.commons.codec.digest.DigestUtils;
import java.nio.charset.StandardCharsets;

public class SHA256Hasher {
    
    public String hash_text(String text) {
        
        return DigestUtils.sha256Hex(text.getBytes(StandardCharsets.UTF_8));
    }
}