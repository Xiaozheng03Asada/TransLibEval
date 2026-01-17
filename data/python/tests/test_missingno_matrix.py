import unittest
from function_missingno_matrix import MissingVisualizer

class TestMissingVisualizer(unittest.TestCase):
    def test_all_present(self):
        result = MissingVisualizer().analyze_missing_matrix("1,2,3,4,5")
        self.assertEqual(result, "Total: 5, Missing: 0 (0.0%)")

    def test_all_missing(self):
        result = MissingVisualizer().analyze_missing_matrix("None,None,None")
        self.assertEqual(result, "Total: 3, Missing: 3 (100.0%)")

    def test_some_missing(self):
        result = MissingVisualizer().analyze_missing_matrix("1,None,3")
        self.assertEqual(result, "Total: 3, Missing: 1 (33.33%)")

    def test_empty_input(self):
        result = MissingVisualizer().analyze_missing_matrix("")
        self.assertEqual(result, "Total: 1, Missing: 0 (0.0%)")

    def test_with_spaces(self):
        result = MissingVisualizer().analyze_missing_matrix(" None , 2 , None ")
        self.assertEqual(result, "Total: 3, Missing: 2 (66.67%)")

if __name__ == "__main__":
    unittest.main()
