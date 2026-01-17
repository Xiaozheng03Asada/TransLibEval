package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_shapely_buffer {
    private function_shapely_buffer geometryBuffer;

    @BeforeEach
    void setUp() {
        geometryBuffer = new function_shapely_buffer();
    }

    @Test
    void test_point_buffer() {
        String geom = "POINT(0 0)";
        float distance = 1.0f;
        String expectedStart = "POLYGON";
        String result = geometryBuffer.calculate_buffer(geom, distance);
        assertTrue(result.startsWith(expectedStart));
    }

    @Test
    void test_polygon_buffer() {
        String geom = "POLYGON((0 0, 2 0, 1 2, 0 0))";
        float distance = 0.5f;
        String expectedStart = "POLYGON";
        String result = geometryBuffer.calculate_buffer(geom, distance);
        assertTrue(result.startsWith(expectedStart));
    }

    @Test
    void test_negative_buffer() {
        String geom = "POLYGON((0 0, 4 0, 2 4, 0 0))";
        float distance = -0.5f;
        String expectedStart = "POLYGON";
        String result = geometryBuffer.calculate_buffer(geom, distance);
        assertTrue(result.startsWith(expectedStart));
    }

    @Test
    void test_empty_geometry() {
        String geom = "GEOMETRYCOLLECTION EMPTY";
        float distance = 1.0f;
        String expected = "GEOMETRYCOLLECTION EMPTY";
        assertEquals(expected, geometryBuffer.calculate_buffer(geom, distance));
    }

    @Test
    void test_invalid_geometry() {
        String geom = "INVALID GEOMETRY";
        float distance = 1.0f;
        String expected = "Error: Could not process the input geometry.";
        assertEquals(expected, geometryBuffer.calculate_buffer(geom, distance));
    }
}