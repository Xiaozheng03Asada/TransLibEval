package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_autopep8_fix_code {
    private function_autopep8_fix_code formatter;

    @BeforeEach
    void setUp() {
        formatter = new function_autopep8_fix_code();
    }

    @Test
    void test_simple_code() {
        String code = "public class Test{void method(int x,int y){System.out.println(x+y);}}";
        String formatted_code = formatter.format_code(code);
        assertTrue(formatted_code.contains("void method(int x, int y)"));
    }

    @Test
    void test_multiple_issues_code() {
        String code = "public   class   Test{void   method(int   a,int   b){int   x=a+b;}}";
        String formatted_code = formatter.format_code(code);
        assertTrue(formatted_code.contains("void method(int a, int b)"));
    }

    @Test
    void test_already_formatted_code() {
        String code = "public class Test {\n  void method(int a, int b) {\n    int x = a + b;\n  }\n}\n";
        String formatted_code = formatter.format_code(code);
        assertNotNull(formatted_code);
    }

    @Test
    void test_invalid_code() {
        String code = "{\"name\": \"Alice\", \"age\": 30}";
        String formatted_code = formatter.format_code(code);
        assertEquals(code + "\n", formatted_code);
    }

    @Test
    void test_syntax_error_code() {
        String code = "public class Test { void method(int x, int y) { return x + y;";
        try {
            String formatted_code = formatter.format_code(code);
            assertNotNull(formatted_code);
        } catch (Exception e) {
            fail("Formatter failed with error: " + e.getMessage());
        }
    }
}