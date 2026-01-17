package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_shapely_intersects {
    private function_shapely_intersects geometryIntersects;

    @BeforeEach
    void setUp() {
        geometryIntersects = new function_shapely_intersects();
    }

    @Test
    void test_intersecting_polygons() {
        String geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
        String geom2 = "POLYGON((1 0, 3 0, 2 2, 1 0))";
        assertTrue(geometryIntersects.check_intersects(geom1, geom2));
    }

    @Test
    void test_non_intersecting_polygons() {
        String geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
        String geom2 = "POLYGON((3 0, 5 0, 4 2, 3 0))";
        assertFalse(geometryIntersects.check_intersects(geom1, geom2));
    }

    @Test
    void test_intersecting_line_and_polygon() {
        String geom1 = "LINESTRING(0 0, 2 2)";
        String geom2 = "POLYGON((1 1, 3 1, 2 3, 1 1))";
        assertTrue(geometryIntersects.check_intersects(geom1, geom2));
    }

    @Test
    void test_disjoint_line_and_polygon() {
        String geom1 = "LINESTRING(0 0, 1 1)";
        String geom2 = "POLYGON((2 2, 4 2, 3 4, 2 2))";
        assertFalse(geometryIntersects.check_intersects(geom1, geom2));
    }

    @Test
    void test_invalid_geometry() {
        String geom1 = "INVALID GEOMETRY";
        String geom2 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
        assertFalse(geometryIntersects.check_intersects(geom1, geom2));
    }
}