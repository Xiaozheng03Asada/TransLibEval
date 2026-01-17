package com.example;

import org.apache.commons.codec.digest.DigestUtils;


public class HashingExample {


    public String compute_hash(String input_data) {
        // 若输入为null，转为字符串"null"
        String inputStr = String.valueOf(input_data);
        // 使用Apache Commons Codec计算MD5哈希值
        return DigestUtils.md5Hex(inputStr);
    }
}