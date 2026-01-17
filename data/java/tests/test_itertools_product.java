package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class test_itertools_product {
    private function_itertools_product processor;

    @BeforeEach
    void setUp() {
        processor = new function_itertools_product();
    }

    @Test
    void testCartesianProductOneElementInThreeLists() {
        String input = "1;2,3;4,5";
        String expectedResult = "124;125;134;135";
        String result = processor.test_product(input);
        assertEquals(
                Arrays.stream(expectedResult.split(";")).sorted().collect(Collectors.joining(";")),
                Arrays.stream(result.split(";")).sorted().collect(Collectors.joining(";"))
        );
    }

    @Test
    void testCartesianProductNegativeNumbers() {
        String input = "-1,0;-2,2";
        String expectedResult = "-1-2;-12;0-2;02";
        String result = processor.test_product(input);
        assertEquals(
                Arrays.stream(expectedResult.split(";")).sorted().collect(Collectors.joining(";")),
                Arrays.stream(result.split(";")).sorted().collect(Collectors.joining(";"))
        );
    }

    @Test
    void testCartesianProductNoneInput() {
        assertThrows(IllegalArgumentException.class, () -> processor.test_product(null));
    }

    @Test
    void testCartesianProductNonIterableInput() {
        // 修改这里：将整数123转换为字符串"123"
        assertThrows(IllegalArgumentException.class, () -> processor.test_product("123"));
    }

    @Test
    void testCartesianProductElementsNotIterable() {
        assertThrows(IllegalArgumentException.class, () -> processor.test_product("1,2;;4,5"));
    }
}