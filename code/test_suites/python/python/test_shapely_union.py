import unittest
from function_shapely_union import GeometryUnion

class TestGeometryUnion(unittest.TestCase):
    def setUp(self):
        self.geometry_union = GeometryUnion()

    def test_disjoint_geometries(self):
        geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))"
        geom2 = "POLYGON((3 0, 5 0, 4 2, 3 0))"
        expected = "MULTIPOLYGON (((0 0, 2 0, 1 2, 0 0)), ((3 0, 5 0, 4 2, 3 0)))"
        result = self.geometry_union.calculate_union(geom1, geom2)
        self.assertIn("MULTIPOLYGON", result)
        self.assertIn("((0 0, 2 0, 1 2, 0 0))", result)
        self.assertIn("((3 0, 5 0, 4 2, 3 0))", result)

    def test_overlapping_geometries(self):
        geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))"
        geom2 = "POLYGON((1 0, 3 0, 2 2, 1 0))"
        expected_contains = [
            "POLYGON",
            "(0 0, 1 2, 1.5 1, 2 2, 3 0, 2 0, 1 0, 0 0)",
        ]
        result = self.geometry_union.calculate_union(geom1, geom2)
        for part in expected_contains:
            self.assertIn(part, result)

    def test_geometry_collection(self):
        geom1 = "POINT(1 1)"
        geom2 = "POLYGON((0 0, 2 0, 1 2, 0 0))"
        
        expected_contains = [
            "POLYGON ((1 2, 2 0, 0 0, 1 2))",  
            "GEOMETRYCOLLECTION",              
        ]
        result = self.geometry_union.calculate_union(geom1, geom2)
        
        self.assertTrue(
            any(expected in result for expected in expected_contains),
            f"Unexpected result: {result}"
        )


    def test_invalid_geometry(self):
        geom1 = "INVALID GEOMETRY"
        geom2 = "POLYGON((0 0, 2 0, 1 2, 0 0))"
        expected = "Error: Could not create geometry because of errors while reading input."
        self.assertEqual(self.geometry_union.calculate_union(geom1, geom2), expected)

    def test_empty_geometries(self):
        geom1 = "GEOMETRYCOLLECTION EMPTY"
        geom2 = "GEOMETRYCOLLECTION EMPTY"
        expected = "GEOMETRYCOLLECTION EMPTY"
        self.assertEqual(self.geometry_union.calculate_union(geom1, geom2), expected)

if __name__ == "__main__":
    unittest.main()
