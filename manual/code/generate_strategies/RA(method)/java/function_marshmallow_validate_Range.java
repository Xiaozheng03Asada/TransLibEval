package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

public class UserValidator {

    // 该方法使用 Jackson 解析 JSON，并校验 age 和 score 的取值范围
    public String validate_user_data(String user_data) {
        try {
            // 创建 ObjectMapper 实例
            ObjectMapper mapper = new ObjectMapper();
            // 设置反序列化时如果遇到未知属性时抛异常
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            // 局部内部类，模拟 Python 中定义的 UserSchema
            class User {
                public int age;      // 必须存在且为 int 类型
                public double score; // 必须存在且为 float 类型
            }

            // 将 JSON 字符串解析成 User 对象
            User user = mapper.readValue(user_data, User.class);

            // 手动验证 age 和 score 的取值范围
            if (user.age < 0 || user.age > 120 || user.score < 0.0 || user.score > 100.0) {
                return "Error: invalid input";
            }

            // 序列化对象为 JSON 字符串返回
            return mapper.writeValueAsString(user);
        } catch (Exception e) {
            // 解析失败或验证不通过返回错误信息
            return "Error: invalid input";
        }
    }
}