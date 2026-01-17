package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;

public class test_bitarray_invert {

    @Test
    public void test_invert_all_bits() {
        function_bitarray_invert func = new function_bitarray_invert();
        String result = func.invert_bitarray("101010", null, null);
        assertEquals("010101", result);
    }

    @Test
    public void test_invert_partial_bits() {
        function_bitarray_invert func = new function_bitarray_invert();
        String result = func.invert_bitarray("111000", 1, 4);
        assertEquals("100100", result);
    }

    @Test
    public void test_invert_second_half() {
        function_bitarray_invert func = new function_bitarray_invert();
        String result = func.invert_bitarray("110011", 3, 6);
        assertEquals("110100", result);
    }

    @Test
    public void test_invert_large_alternating_pattern() {
        function_bitarray_invert func = new function_bitarray_invert();
        StringBuilder bits = new StringBuilder();
        StringBuilder expected = new StringBuilder();
        for (int i = 0; i < 5000; i++) {
            bits.append("01");
            expected.append("10");
        }
        String result = func.invert_bitarray(bits.toString(), null, null);
        assertEquals(expected.toString(), result);
    }

    @Test
    public void test_invert_non_integer_indices() throws Exception {
        function_bitarray_invert func = new function_bitarray_invert();
        // Retrieve the method with parameter types (String, Integer, Integer)
        Method method = function_bitarray_invert.class.getMethod("invert_bitarray", String.class, Integer.class, Integer.class);
        // Passing a non-integer (1.5 as a Double) should throw an IllegalArgumentException due to argument type mismatch.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            method.invoke(func, "101010", 1.5, 4);
        });
        assertNotNull(exception);
    }
}