package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class test_spacy_vector {

    private function_spacy_vector processor;

    @BeforeEach
    void setUp() {
        processor = new function_spacy_vector();
    }

    @Test
    void testFunctionCalled() {
        // 确认函数被调用并返回结果
        String text = "hello world";
        String result = processor.vector_lengths(text);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    void testGetVectorLengthsLengthMatchesTokens() {
        String text = "hello world";
        String result = processor.vector_lengths(text);
        // 确保结果包含两个token的向量长度
        String[] tokens = result.split(", ");
        assertEquals(2, tokens.length);
        assertTrue(result.contains("hello:"));
        assertTrue(result.contains("world:"));
    }

    @Test
    void testVectorLengthsWithPunctuation() {
        String text = "hello, world!";
        String result = processor.vector_lengths(text);
        String[] tokens = result.split(", ");
        assertEquals(2, tokens.length);  // 保持与原Python测试相同的逻辑，检查标点符号处理
    }

    @Test
    void testVectorLengthsCaseSensitivity() {
        String text1 = "Hello";
        String text2 = "hello";
        String result1 = processor.vector_lengths(text1);
        String result2 = processor.vector_lengths(text2);

        // 解析出词向量长度
        double length1 = Double.parseDouble(result1.split(": ")[1]);
        double length2 = Double.parseDouble(result2.split(": ")[1]);

        assertEquals(1, result1.split(", ").length);
        assertEquals(1, result2.split(", ").length);
        // 由于我们的实现保持了大小写敏感性，这里保持与Python版本相同的断言
        assertNotEquals(length1, length2);
    }

    @Test
    void testOutputType() {
        String text = "hello world";
        String result = processor.vector_lengths(text);
        assertInstanceOf(String.class, result);
    }
}