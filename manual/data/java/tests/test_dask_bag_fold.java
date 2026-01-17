package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class test_dask_bag_fold {

    private function_dask_bag_fold cumsumFunc;

    @BeforeEach
    void setUp() {
        cumsumFunc = new function_dask_bag_fold();
    }

    @Test
    void testBasicSum() {
        String result = cumsumFunc.cumulative_sum("1,2,3,4,5");
        assertEquals("15.0", result);
    }

    @Test
    void testNegativeNumbers() {
        String result = cumsumFunc.cumulative_sum("10,-2,-3,5");
        assertEquals("10.0", result);
    }

    @Test
    void testAlternatingSigns() {
        String result = cumsumFunc.cumulative_sum("1,-1,2,-2,3,-3");
        assertEquals("0.0", result);
    }

    @Test
    void testLargeNumbers() {
        String result = cumsumFunc.cumulative_sum("1000000,1000000,1000000");
        assertEquals("3000000.0", result);
    }

    @Test
    void testInvalidInput() {
        String result = cumsumFunc.cumulative_sum("1,a,None,4");
        assertEquals("Error", result);
    }
}