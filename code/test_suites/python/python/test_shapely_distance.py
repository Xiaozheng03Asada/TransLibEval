import unittest
from function_shapely_distance import GeometryDistance
from shapely.geometry import Point, LineString, Polygon

class TestGeometryDistance(unittest.TestCase):
    def setUp(self):
        self.geometry_distance = GeometryDistance()

    def test_point_to_point(self):
        geom1 = "POINT(0 0)"
        geom2 = "POINT(3 4)"
        expected = 5.0  
        self.assertEqual(self.geometry_distance.calculate_distance(geom1, geom2), expected)

    def test_point_to_line(self):
        geom1 = "POINT(1 1)"
        geom2 = "LINESTRING(0 0, 0 2)"
        expected = 1.0 
        self.assertEqual(self.geometry_distance.calculate_distance(geom1, geom2), expected)

    def test_point_to_polygon(self):
        geom1 = "POINT(5 5)"
        geom2 = "POLYGON((0 0, 4 0, 4 4, 0 4, 0 0))"
        expected = 1.4142135623730951  # Diagonal distance to the polygon
        self.assertEqual(self.geometry_distance.calculate_distance(geom1, geom2), expected)

    def test_intersecting_objects(self):
        geom1 = "LINESTRING(0 0, 2 2)"
        geom2 = "POLYGON((1 1, 3 1, 3 3, 1 3, 1 1))"
        expected = 0.0  # The line and polygon intersect
        self.assertEqual(self.geometry_distance.calculate_distance(geom1, geom2), expected)

    def test_invalid_geometry(self):
        geom1 = "INVALID GEOMETRY"
        geom2 = "POINT(0 0)"
        expected = -1.0  
        self.assertEqual(self.geometry_distance.calculate_distance(geom1, geom2), expected)

if __name__ == "__main__":
    unittest.main()
