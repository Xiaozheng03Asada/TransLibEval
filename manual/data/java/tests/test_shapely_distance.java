package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_shapely_distance {
    private function_shapely_distance solution;

    @BeforeEach
    void setUp() {
        solution = new function_shapely_distance();
    }

    @Test
    void test_point_to_point() {
        String geom1 = "POINT(0 0)";
        String geom2 = "POINT(3 4)";
        double expected = 5.0;
        assertEquals(expected, solution.solution(geom1, geom2), 0.0000001);
    }

    @Test
    void test_point_to_line() {
        String geom1 = "POINT(1 1)";
        String geom2 = "LINESTRING(0 0, 0 2)";
        double expected = 1.0;
        assertEquals(expected, solution.solution(geom1, geom2), 0.0000001);
    }

    @Test
    void test_point_to_polygon() {
        String geom1 = "POINT(5 5)";
        String geom2 = "POLYGON((0 0, 4 0, 4 4, 0 4, 0 0))";
        double expected = 1.4142135623730951;
        assertEquals(expected, solution.solution(geom1, geom2), 0.0000001);
    }

    @Test
    void test_intersecting_objects() {
        String geom1 = "LINESTRING(0 0, 2 2)";
        String geom2 = "POLYGON((1 1, 3 1, 3 3, 1 3, 1 1))";
        double expected = 0.0;
        assertEquals(expected, solution.solution(geom1, geom2), 0.0000001);
    }

    @Test
    void test_invalid_geometry() {
        String geom1 = "INVALID GEOMETRY";
        String geom2 = "POINT(0 0)";
        double expected = -1.0;
        assertEquals(expected, solution.solution(geom1, geom2), 0.0000001);
    }
}