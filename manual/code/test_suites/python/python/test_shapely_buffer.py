import unittest
from function_shapely_buffer import GeometryBuffer

class TestGeometryBuffer(unittest.TestCase):
    def setUp(self):
        self.geometry_buffer = GeometryBuffer()

    def test_point_buffer(self):
        geom = "POINT(0 0)"
        distance = 1.0
        expected_start = "POLYGON"
        result = self.geometry_buffer.calculate_buffer(geom, distance)
        self.assertTrue(result.startswith(expected_start))

    def test_polygon_buffer(self):
        geom = "POLYGON((0 0, 2 0, 1 2, 0 0))"
        distance = 0.5
        expected_start = "POLYGON"
        result = self.geometry_buffer.calculate_buffer(geom, distance)
        self.assertTrue(result.startswith(expected_start))

    def test_negative_buffer(self):
        geom = "POLYGON((0 0, 4 0, 2 4, 0 0))"
        distance = -0.5
        expected_start = "POLYGON"
        result = self.geometry_buffer.calculate_buffer(geom, distance)
        self.assertTrue(result.startswith(expected_start))

    def test_empty_geometry(self):
        geom = "GEOMETRYCOLLECTION EMPTY"
        distance = 1.0
        expected = "GEOMETRYCOLLECTION EMPTY"
        self.assertEqual(self.geometry_buffer.calculate_buffer(geom, distance), expected)

    def test_invalid_geometry(self):
        geom = "INVALID GEOMETRY"
        distance = 1.0
        expected = "Error: Could not process the input geometry."
        self.assertEqual(self.geometry_buffer.calculate_buffer(geom, distance), expected)

if __name__ == "__main__":
    unittest.main()
