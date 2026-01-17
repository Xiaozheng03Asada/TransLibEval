import unittest
from function_matplotlib_bar import BarPlotGenerator

class TestBarPlotGenerator(unittest.TestCase):
    def test_valid_input(self):
        result = BarPlotGenerator.create_bar_plot("A,B,C", "10,20,30")
        self.assertEqual(result, "Plot saved as 'bar_plot.png'.")

    def test_empty_input(self):
        result = BarPlotGenerator.create_bar_plot("", "")
        self.assertEqual(result, "Error: Categories and values cannot be empty.")

    def test_mismatched_lengths(self):
        result = BarPlotGenerator.create_bar_plot("A,B", "10")
        self.assertEqual(result, "Error: Categories and values must have the same length.")

    def test_non_string_categories(self):
        result = BarPlotGenerator.create_bar_plot("1,2,3", "10,20,30")
        self.assertEqual(result, "Plot saved as 'bar_plot.png'.")

    def test_non_numeric_values(self):
        result = BarPlotGenerator.create_bar_plot("A,B,C", "10,'20',30")
        self.assertEqual(result, "Plot saved as 'bar_plot.png'.")

if __name__ == "__main__":
    unittest.main()
