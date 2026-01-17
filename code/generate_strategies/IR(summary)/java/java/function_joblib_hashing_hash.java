package com.example;

import org.apache.commons.codec.digest.DigestUtils;


public class HashingExample {


    public String compute_hash(String input_data) {
        
        String inputStr = String.valueOf(input_data);
        
        return DigestUtils.md5Hex(inputStr);
    }
}