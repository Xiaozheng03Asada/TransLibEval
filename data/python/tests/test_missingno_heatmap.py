import unittest
from function_missingno_heatmap import MissingVisualizer

class TestMissingVisualizer(unittest.TestCase):
    def test_all_present(self):
        result = MissingVisualizer().analyze_missing_heatmap("1;2;3;4;5")
        self.assertEqual(result, "Correlation between col_1 and col_2: Low; Correlation between col_1 and col_3: Low; Correlation between col_1 and col_4: Low; Correlation between col_1 and col_5: Low; Correlation between col_2 and col_1: Low; Correlation between col_2 and col_3: Low; Correlation between col_2 and col_4: Low; Correlation between col_2 and col_5: Low; Correlation between col_3 and col_1: Low; Correlation between col_3 and col_2: Low; Correlation between col_3 and col_4: Low; Correlation between col_3 and col_5: Low; Correlation between col_4 and col_1: Low; Correlation between col_4 and col_2: Low; Correlation between col_4 and col_3: Low; Correlation between col_4 and col_5: Low; Correlation between col_5 and col_1: Low; Correlation between col_5 and col_2: Low; Correlation between col_5 and col_3: Low; Correlation between col_5 and col_4: Low")

    def test_all_missing(self):
        result = MissingVisualizer().analyze_missing_heatmap("None;None;None")
        self.assertEqual(result, "Correlation between col_1 and col_2: High; Correlation between col_1 and col_3: High; Correlation between col_2 and col_1: High; Correlation between col_2 and col_3: High; Correlation between col_3 and col_1: High; Correlation between col_3 and col_2: High")

    def test_some_missing(self):
        result = MissingVisualizer().analyze_missing_heatmap("1;None;3")
        self.assertEqual(result, "Correlation between col_1 and col_2: High; Correlation between col_1 and col_3: High; Correlation between col_2 and col_1: High; Correlation between col_2 and col_3: High; Correlation between col_3 and col_1: High; Correlation between col_3 and col_2: High")

    def test_empty_input(self):
        result = MissingVisualizer().analyze_missing_heatmap("")
        self.assertEqual(result, "Error: list index out of range")

    def test_with_spaces(self):
        result = MissingVisualizer().analyze_missing_heatmap(" None ; 2 ; None ")
        self.assertEqual(result, "Correlation between col_1 and col_2: High; Correlation between col_1 and col_3: High; Correlation between col_2 and col_1: High; Correlation between col_2 and col_3: High; Correlation between col_3 and col_1: High; Correlation between col_3 and col_2: High")

if __name__ == "__main__":
    unittest.main()
