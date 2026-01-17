package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test_functools_singledispatch {

    @Test
    public void test_test_singledispatch_str() {
        function_functools_singledispatch processor = new function_functools_singledispatch();
        Object result = processor.test_singledispatch("hello");
        assertEquals("HELLO", result);
    }

    @Test
    public void test_test_singledispatch_int() {
        function_functools_singledispatch processor = new function_functools_singledispatch();
        Object result = processor.test_singledispatch(3);
        assertEquals(6, result);
    }

    @Test
    public void test_test_singledispatch_float() {
        function_functools_singledispatch processor = new function_functools_singledispatch();
        Object result = processor.test_singledispatch(2.5);
        assertEquals(5.0, (Double) result, 0.0);
    }

    @Test
    public void test_test_singledispatch_none() {
        function_functools_singledispatch processor = new function_functools_singledispatch();
        Object result = processor.test_singledispatch(null);
        assertEquals("NONE", result);
    }

    @Test
    public void test_test_singledispatch_unsupported_type() {
        function_functools_singledispatch processor = new function_functools_singledispatch();
        assertThrows(UnsupportedOperationException.class, () -> {
            processor.test_singledispatch(new java.util.HashSet<>());
        });
    }
}