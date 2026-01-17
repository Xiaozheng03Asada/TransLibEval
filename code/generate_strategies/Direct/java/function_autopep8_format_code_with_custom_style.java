package com.example;

import org.apache.commons.lang3.StringUtils;

public class CodeFormatter {
    public String format_code_with_custom_style(String code) {
        // 模拟 autopep8.fix_code 功能：使用第三方依赖 Apache Commons Lang3 的 substring 方法返回原字符串（不做任何实际修改）
        String fixed_code = StringUtils.substring(code, 0);
        return fixed_code;
    }
}