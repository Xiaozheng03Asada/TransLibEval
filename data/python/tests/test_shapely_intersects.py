import unittest
from function_shapely_intersects import GeometryIntersects

class TestGeometryIntersects(unittest.TestCase):
    def setUp(self):
        self.geometry_intersects = GeometryIntersects()

    def test_intersecting_polygons(self):
        geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))"
        geom2 = "POLYGON((1 0, 3 0, 2 2, 1 0))"
        self.assertTrue(self.geometry_intersects.check_intersects(geom1, geom2))

    def test_non_intersecting_polygons(self):
        geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))"
        geom2 = "POLYGON((3 0, 5 0, 4 2, 3 0))"
        self.assertFalse(self.geometry_intersects.check_intersects(geom1, geom2))

    def test_intersecting_line_and_polygon(self):
        geom1 = "LINESTRING(0 0, 2 2)"
        geom2 = "POLYGON((1 1, 3 1, 2 3, 1 1))"
        self.assertTrue(self.geometry_intersects.check_intersects(geom1, geom2))

    def test_disjoint_line_and_polygon(self):
        geom1 = "LINESTRING(0 0, 1 1)"
        geom2 = "POLYGON((2 2, 4 2, 3 4, 2 2))"
        self.assertFalse(self.geometry_intersects.check_intersects(geom1, geom2))

    def test_invalid_geometry(self):
        geom1 = "INVALID GEOMETRY"
        geom2 = "POLYGON((0 0, 2 0, 1 2, 0 0))"
        self.assertFalse(self.geometry_intersects.check_intersects(geom1, geom2))

if __name__ == "__main__":
    unittest.main()
