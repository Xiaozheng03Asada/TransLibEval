import unittest
from function_networkx_dijkstra_path import NetworkXExample

class TestNetworkXExample(unittest.TestCase):

    def test_shortest_path(self):
        calc = NetworkXExample()
        result = calc.compute_shortest_path('A', 'C', 'A', 'B', 1, 'B', 'C', 1)
        self.assertEqual(result, 'A -> B -> C')  # Expected shortest path is A -> B -> C

    def test_no_path(self):
        calc = NetworkXExample()
        result = calc.compute_shortest_path('A', 'D', 'A', 'B', 1, 'B', 'C', 2)
        self.assertEqual(result, 'no path')  # No path from A to D

    def test_single_edge(self):
        calc = NetworkXExample()
        result = calc.compute_shortest_path('A', 'B', 'A', 'B', 5)
        self.assertEqual(result, 'A -> B')  # Only one edge, path is just A -> B

    def test_multiple_paths(self):
        calc = NetworkXExample()
        result = calc.compute_shortest_path('A', 'C', 'A', 'B', 1, 'B', 'C', 2)
        self.assertEqual(result, 'A -> B -> C')  # Path with multiple edges

    def test_multiple_edges_with_different_weights(self):
        calc = NetworkXExample()
        result = calc.compute_shortest_path('A', 'C', 'A', 'B', 5, 'B', 'C', 1)
        self.assertEqual(result, 'A -> B -> C')  # Shortest path is A -> B -> C with weight 1

if __name__ == '__main__':
    unittest.main()
