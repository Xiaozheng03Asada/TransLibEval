package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_shapely_union {
    private function_shapely_union geometryUnion;

    @BeforeEach
    void setUp() {
        geometryUnion = new function_shapely_union();
    }

    @Test
    void test_disjoint_geometries() {
        String geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
        String geom2 = "POLYGON((3 0, 5 0, 4 2, 3 0))";
        String result = geometryUnion.calculate_union(geom1, geom2);

        // 打印结果用于调试
        System.out.println("Result of union: " + result);

        // 验证基本结构
        assertTrue(result.startsWith("MULTIPOLYGON ("), "Should start with MULTIPOLYGON");
        assertTrue(result.endsWith(")"), "Should end with )");

        // 验证包含两个多边形
        assertTrue(result.contains("((0 0, 2 0, 1 2, 0 0))"), "Should contain first polygon");
        assertTrue(result.contains("((3 0, 5 0, 4 2, 3 0))"), "Should contain second polygon");

        // 验证完整的预期格式
        String expected = "MULTIPOLYGON (((0 0, 2 0, 1 2, 0 0)), ((3 0, 5 0, 4 2, 3 0)))";
        assertEquals(
                expected.replaceAll("\\s+", ""),
                result.replaceAll("\\s+", ""),
                "Complete format should match"
        );
    }

    @Test
    void test_overlapping_geometries() {
        String geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
        String geom2 = "POLYGON((1 0, 3 0, 2 2, 1 0))";
        String result = geometryUnion.calculate_union(geom1, geom2);
        assertTrue(result.contains("POLYGON"));
    }

    @Test
    void test_geometry_collection() {
        String geom1 = "POINT(1 1)";
        String geom2 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
        String result = geometryUnion.calculate_union(geom1, geom2);
        assertTrue(
                result.contains("POLYGON") || result.contains("GEOMETRYCOLLECTION"),
                "Unexpected result: " + result
        );
    }

    @Test
    void test_invalid_geometry() {
        String geom1 = "INVALID GEOMETRY";
        String geom2 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
        String expected = "Error: Could not create geometry because of errors while reading input.";
        assertEquals(expected, geometryUnion.calculate_union(geom1, geom2));
    }

    @Test
    void test_empty_geometries() {
        String geom1 = "GEOMETRYCOLLECTION EMPTY";
        String geom2 = "GEOMETRYCOLLECTION EMPTY";
        String expected = "GEOMETRYCOLLECTION EMPTY";
        assertEquals(expected, geometryUnion.calculate_union(geom1, geom2));
    }
}