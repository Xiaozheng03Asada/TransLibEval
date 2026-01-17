package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_dask_dataframe_map_partitions {

    private function_dask_dataframe_map_partitions func;

    @BeforeEach
    public void setUp() {
        func = new function_dask_dataframe_map_partitions();
    }

    @Test
    public void test_valid_column() {
        String data = "A,B\n0,1.5\n1,2.5\n2,3.5\n3,4.5\n4,5.5\n5,6.5\n6,7.5\n7,8.5\n8,9.5\n9,10.5";
        String result = func.compute_partition_means(data, "B");
        assertEquals("3.5,8.5", result);
    }

    @Test
    public void test_invalid_column() {
        String data = "A,B\n0,1.5\n1,2.5\n2,3.5";
        String result = func.compute_partition_means(data, "C");
        assertEquals("Error", result);
    }

    @Test
    public void test_non_numeric_column() {
        String data = "A,B\nx,a\ny,b\nz,c";
        String result = func.compute_partition_means(data, "B");
        assertEquals("Error", result);
    }

    @Test
    public void test_single_value_column() {
        String data = "B\n10";
        String result = func.compute_partition_means(data, "B");
        assertEquals("10.0", result);
    }

    @Test
    public void test_all_same_values() {
        String data = "B\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5";
        String result = func.compute_partition_means(data, "B");
        assertEquals("5.0,5.0", result);
    }
}