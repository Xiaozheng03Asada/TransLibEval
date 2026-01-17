package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;

public class DictMerger {
    public String combine_dicts(String dict1, String dict2) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 使用LinkedHashMap来保持顺序
            Map<String, Object> map1 = mapper.readValue(dict1.replace("'", "\""), LinkedHashMap.class);
            Map<String, Object> map2 = mapper.readValue(dict2.replace("'", "\""), LinkedHashMap.class);

            // 使用LinkedHashMap作为结果容器
            Map<String, Object> result = new LinkedHashMap<>(map1);
            result.putAll(map2);

            // 将结果转换为字符串，并将双引号替换为单引号
            return mapper.writeValueAsString(result).replace("\"", "'");
        } catch (Exception e) {
            try {
                Object test = mapper.readValue(dict1.replace("'", "\""), Object.class);
                if (!(test instanceof Map)) {
                    return "Error: input is not a dictionary";
                }
            } catch (Exception ex) {
                return "Error: invalid input";
            }
            return "Error: invalid input";
        }
    }
}