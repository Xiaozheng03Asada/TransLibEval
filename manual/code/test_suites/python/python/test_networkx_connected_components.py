import unittest
from function_networkx_connected_components import GraphUtils

class TestGraphUtils(unittest.TestCase):

    def test_success(self):
        result = GraphUtils().find_connected_components(5, 4, "A-B, B-C, D-E, E-F")
        self.assertTrue(result.startswith("success"))
        self.assertIn("2 components", result)  # Expected output: Two connected components

    def test_single_component(self):
        result = GraphUtils().find_connected_components(3, 3, "A-B, B-C, A-C")
        self.assertTrue(result.startswith("success"))
        self.assertIn("1 components", result)  # Expected output: One connected component

    def test_no_edges(self):
        result = GraphUtils().find_connected_components(5, 0, "")
        self.assertTrue(result.startswith("success"))
        self.assertIn("5 components", result)  # Expected output: Each node is a separate component

    def test_single_node(self):
        result = GraphUtils().find_connected_components(1, 0, "")
        self.assertTrue(result.startswith("success"))
        self.assertIn("1 components", result)  # Expected output: Only one component (the node itself)

    def test_partial_connection(self):
        result = GraphUtils().find_connected_components(5, 3, "A-B, B-C, D-E")
        self.assertTrue(result.startswith("success"))
        self.assertIn("2 components", result)  # Expected output: Two connected components

if __name__ == "__main__":
    unittest.main()
