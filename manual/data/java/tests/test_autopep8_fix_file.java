package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_autopep8_fix_file {
    private function_autopep8_fix_file formatter;

    @BeforeEach
    void setUp() {
        formatter = new function_autopep8_fix_file();
    }

    @Test
    void test_format_java_code_basic() {
        String code = "class Test{void method(int x,int y){System.out.println(x+y);}}";
        String expected = "class Test {\n  void method(int x, int y) {\n    System.out.println(x + y);\n  }\n}\n";
        assertEquals(expected, formatter.format_code(code));
    }

    @Test
    void test_format_java_code_with_empty_lines() {
        String code = "\nclass Test{\nvoid method(int x,int y){System.out.println(x+y);}}\n";
        String expected = "class Test {\n  void method(int x, int y) {\n    System.out.println(x + y);\n  }\n}\n";
        assertEquals(expected, formatter.format_code(code));
    }

    @Test
    void test_format_java_code_mixed_indentation() {
        String code = "class Test{\n\tvoid method(int x,int y){\n\t\tSystem.out.println(x+y);}}";
        String expected = "class Test {\n  void method(int x, int y) {\n    System.out.println(x + y);\n  }\n}\n";
        assertEquals(expected, formatter.format_code(code));
    }

    @Test
    void test_format_java_code_no_function() {
        String code = "int x=10;int y=20;int z=x+y;System.out.println(z);";
        String expected = "int x = 10;\nint y = 20;\nint z = x + y;\nSystem.out.println(z);\n";
        assertEquals(expected, formatter.format_code(code));
    }

    @Test
    void test_format_java_code_single_line() {
        String code = "class Test{void method(){int x=1;int y=2;int z=x+y;System.out.println(z);}}";
        String expected = "class Test {\n  void method() {\n    int x = 1;\n    int y = 2;\n    int z = x + y;\n    System.out.println(z);\n  }\n}\n";
        assertEquals(expected, formatter.format_code(code));
    }
}