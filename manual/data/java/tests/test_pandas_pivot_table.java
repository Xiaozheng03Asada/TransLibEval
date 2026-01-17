package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test_pandas_pivot_table {
    @Test
    public void test_success() {
        function_pandas_pivot_table obj = new function_pandas_pivot_table();
        String result = obj.create_pivot_table(null, null, null, null, null, null);
        assertTrue(result.startsWith("Date:"), "结果应以 'Date:' 开头");
    }

    @Test
    public void test_pivot_table_structure() {
        function_pandas_pivot_table obj = new function_pandas_pivot_table();
        String result = obj.create_pivot_table(null, null, null, null, null, null);
        assertTrue(result.contains("Date:"), "结果中应包含 'Date:'");
    }

    @Test
    public void test_column_names() {
        function_pandas_pivot_table obj = new function_pandas_pivot_table();
        String result = obj.create_pivot_table(null, null, null, null, null, null);
        // 检查透视表中列名是否正确
        assertTrue(result.contains("Category A:"), "结果中应包含 'Category A:'");
        assertTrue(result.contains("Category B:"), "结果中应包含 'Category B:'");
    }

    @Test
    public void test_result_format() {
        function_pandas_pivot_table obj = new function_pandas_pivot_table();
        String result = obj.create_pivot_table(null, null, null, null, null, null);
        assertTrue(result instanceof String, "返回类型应为String");
    }

    @Test
    public void test_missing_value_check() {
        function_pandas_pivot_table obj = new function_pandas_pivot_table();
        // 当value2为null时，由于存在null参数则应全部替换为默认值
        // 最终返回的透视表中，对于第一行（"2023-01-01"）没有对应B列数据，将使用0填充
        String result = obj.create_pivot_table("2023-01-01", "2023-01-02", "A", "B", 5, null);
        assertTrue(result.contains("Category B: 0"), "结果中B列应为0");
    }
}