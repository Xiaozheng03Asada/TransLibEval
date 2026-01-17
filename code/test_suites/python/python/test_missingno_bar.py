import unittest
from function_missingno_bar import MissingVisualizer

class TestMissingVisualizer(unittest.TestCase):
    def test_all_present(self):
        result = MissingVisualizer().analyze_missing_bar("1;2;3;4;5")
        self.assertEqual(result, "col_1: 0 (0.0%); col_2: 0 (0.0%); col_3: 0 (0.0%); col_4: 0 (0.0%); col_5: 0 (0.0%)")

    def test_all_missing(self):
        result = MissingVisualizer().analyze_missing_bar("None;None;None")
        self.assertEqual(result, "col_1: 3 (100.0%); col_2: 3 (100.0%); col_3: 3 (100.0%)")

    def test_some_missing(self):
        result = MissingVisualizer().analyze_missing_bar("1;None;3")
        self.assertEqual(result, "col_1: 1 (33.33%); col_2: 1 (33.33%); col_3: 1 (33.33%)")

    def test_empty_input(self):
        result = MissingVisualizer().analyze_missing_bar("")
        self.assertEqual(result, "col_1: 0 (0.0%)")

    def test_with_spaces(self):
        result = MissingVisualizer().analyze_missing_bar(" None ; 2 ; None ")
        self.assertEqual(result, "col_1: 2 (66.67%); col_2: 2 (66.67%); col_3: 2 (66.67%)")

if __name__ == "__main__":
    unittest.main()
