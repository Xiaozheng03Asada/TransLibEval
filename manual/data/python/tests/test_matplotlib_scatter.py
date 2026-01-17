import unittest
from function_matplotlib_scatter import ScatterPlotGenerator

class TestScatterPlotGenerator(unittest.TestCase):
    def test_valid_input(self):
        result = ScatterPlotGenerator.create_scatter_plot("1,2,3", "4,5,6")
        self.assertEqual(result, "Plot saved as 'scatter_plot.png'.")

    def test_empty_input(self):
        result = ScatterPlotGenerator.create_scatter_plot("", "")
        self.assertEqual(result, "Error: x_values and y_values cannot be empty.")

    def test_mismatched_lengths(self):
        result = ScatterPlotGenerator.create_scatter_plot("1,2", "3")
        self.assertEqual(result, "Error: x_values and y_values must have the same length.")

    def test_non_numeric_values(self):
        result = ScatterPlotGenerator.create_scatter_plot("1,'2',3", "4,5,6")
        self.assertEqual(result, "Error: All x and y values must be numbers.")

    def test_large_input(self):
        x_values = ",".join(map(str, range(1000)))
        y_values = ",".join(map(str, [val * 2 for val in range(1000)]))
        result = ScatterPlotGenerator.create_scatter_plot(x_values, y_values)
        self.assertEqual(result, "Plot saved as 'scatter_plot.png'.")

if __name__ == "__main__":
    unittest.main()
