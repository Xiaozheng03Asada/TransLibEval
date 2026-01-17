import unittest
from function_matplotlib_pie import PieChartGenerator

class TestPieChartGenerator(unittest.TestCase):
    def test_valid_input(self):
        result = PieChartGenerator.create_pie_chart("A,B,C", "30,40,30")
        self.assertEqual(result, "Pie chart saved as 'pie_chart.png'.")

    def test_empty_input(self):
        result = PieChartGenerator.create_pie_chart("", "")
        self.assertEqual(result, "Error: labels and sizes cannot be empty.")

    def test_mismatched_lengths(self):
        result = PieChartGenerator.create_pie_chart("A,B", "30")
        self.assertEqual(result, "Error: labels and sizes must have the same length.")

    def test_non_string_labels(self):
        result = PieChartGenerator.create_pie_chart("1,B,C", "30,40,30")
        self.assertEqual(result, "Pie chart saved as 'pie_chart.png'.")

    def test_negative_size(self):
        result = PieChartGenerator.create_pie_chart("A,B,C", "30,-40,30")
        self.assertEqual(result, "Error: All sizes must be non-negative numbers.")

if __name__ == "__main__":
    unittest.main()
