import unittest
from function_missingno_dendrogram import MissingVisualizer

class TestMissingVisualizer(unittest.TestCase):
    def test_all_present(self):
        result = MissingVisualizer().analyze_missing_dendrogram("1;2;3;4;5")
        self.assertEqual(result, "col_1: Missing 0, Non-Missing 5; col_2: Missing 0, Non-Missing 5; col_3: Missing 0, Non-Missing 5; col_4: Missing 0, Non-Missing 5; col_5: Missing 0, Non-Missing 5")

    def test_all_missing(self):
        result = MissingVisualizer().analyze_missing_dendrogram("None;None;None")
        self.assertEqual(result, "col_1: Missing 3, Non-Missing 0; col_2: Missing 3, Non-Missing 0; col_3: Missing 3, Non-Missing 0")

    def test_some_missing(self):
        result = MissingVisualizer().analyze_missing_dendrogram("1;None;3")
        self.assertEqual(result, "col_1: Missing 1, Non-Missing 2; col_2: Missing 1, Non-Missing 2; col_3: Missing 1, Non-Missing 2")

    def test_empty_input(self):
        result = MissingVisualizer().analyze_missing_dendrogram("")
        self.assertEqual(result, "col_1: Missing 0, Non-Missing 1")

    def test_with_spaces(self):
        result = MissingVisualizer().analyze_missing_dendrogram(" None ; 2 ; None ")
        self.assertEqual(result, "col_1: Missing 2, Non-Missing 1; col_2: Missing 2, Non-Missing 1; col_3: Missing 2, Non-Missing 1")

if __name__ == "__main__":
    unittest.main()
