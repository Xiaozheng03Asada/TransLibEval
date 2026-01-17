package com.example;

import org.apache.commons.lang3.StringUtils;

public class CodeFormatter {
    public String format_code_with_custom_style(String code) {
        
        String fixed_code = StringUtils.substring(code, 0);
        return fixed_code;
    }
}