package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class IntegerListType {
    // 所有功能均在唯一的to_primitive方法中实现
    public String to_primitive(String value) {
        // 检查输入字符串是否以"["开头和"]"结尾
        if (value == null || !value.startsWith("[") || !value.endsWith("]")) {
            throw new IllegalArgumentException("Value must be a string representing a list.");
        }

        // 使用Jackson第三方库进行字符串解析
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 解析为整数列表
            List<Integer> list = mapper.readValue(value, new TypeReference<List<Integer>>() {});
            // 循环检查每个元素是否为整数（解析时已保证类型为Integer）
            for (Integer item : list) {
                if (item == null) {
                    throw new IllegalArgumentException("All items in the list must be integers.");
                }
            }
            // 返回列表的字符串表示，与Python中str(list)类似，如"[1, 2, 3]"
            return list.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("Value must be a valid list of integers in string format.");
        }
    }
}