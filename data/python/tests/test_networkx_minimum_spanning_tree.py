import unittest
from function_networkx_minimum_spanning_tree import NetworkXExample

class TestNetworkXExample(unittest.TestCase):

    def test_basic_mst(self):
        calc = NetworkXExample()
        result = calc.compute_minimum_spanning_tree('A', 'B', 4, 'B', 'C', 3)
        self.assertEqual(result, 7.0)  # Expected sum of weights for MST: 4 + 3 = 7

    def test_mst_multiple_edges(self):
        calc = NetworkXExample()
        result = calc.compute_minimum_spanning_tree('A', 'B', 1, 'B', 'C', 2)
        self.assertEqual(result, 3.0)  # Expected sum of weights for MST: 1 + 2 = 3

    def test_mst_no_edges(self):
        calc = NetworkXExample()
        result = calc.compute_minimum_spanning_tree('A', 'B', 0, 'B', 'C', 0)
        self.assertEqual(result, 0.0)  # No edges, so weight sum is 0

    def test_mst_single_edge(self):
        calc = NetworkXExample()
        result = calc.compute_minimum_spanning_tree('A', 'B', 10)
        self.assertEqual(result, 10.0)  # Only one edge with weight 10

    def test_mst_disconnected_graph(self):
        calc = NetworkXExample()
        result = calc.compute_minimum_spanning_tree('A', 'B', 5, 'C', 'D', 7)
        self.assertEqual(result, 12.0)  # Expected sum of weights for MST: 5 + 7 = 12

if __name__ == '__main__':
    unittest.main()
