import unittest
from function_networkx_diameter import GraphUtils

class TestCalculateDiameter(unittest.TestCase):

    def test_success(self):
        result = GraphUtils().calculate_diameter("A-B,B-C,C-D,D-E")
        self.assertTrue(result.startswith("success"))

    def test_diameter_value(self):
        result = GraphUtils().calculate_diameter("A-B,B-C,C-D,D-E")
        if result.startswith("success"):
            diameter = int(result.split(":")[1].strip())
            self.assertEqual(diameter, 4)

    def test_non_numeric(self):
        result = GraphUtils().calculate_diameter("A-B,B-C,C-D,D-E")
        self.assertIsInstance(result, str)

    def test_disconnected_graph(self):
        result = GraphUtils().calculate_diameter("A-B,C-D")
        self.assertEqual(result, "failed")

    def test_empty_graph(self):
        result = GraphUtils().calculate_diameter("")
        self.assertEqual(result, "failed")

if __name__ == '__main__':
    unittest.main()
