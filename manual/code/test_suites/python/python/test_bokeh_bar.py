import unittest
from function_bokeh_bar import Plotter

class TestBokehBarChart(unittest.TestCase):

    def test_bar_chart_creation(self):
        plotter = Plotter()
        result = plotter.create_bar_chart('["A", "B", "C"]', '[4, 5, 6]')
        self.assertEqual(result, "Plot displayed.")

    def test_empty_input(self):
        plotter = Plotter()
        result = plotter.create_bar_chart('[]', '[]')
        self.assertEqual(result, "Error: Invalid input format.")

    def test_invalid_length(self):
        plotter = Plotter()
        result = plotter.create_bar_chart('[1, 2]', '[3]')
        self.assertEqual(result, "Error: x_values and y_values must have the same length.")

    def test_custom_title(self):
        plotter = Plotter()
        result = plotter.create_bar_chart('["A", "B", "C"]', '[4, 5, 6]', "Custom Bar Chart Title")
        self.assertEqual(result, "Plot displayed.")

    def test_negative_values(self):
        plotter = Plotter()
        result = plotter.create_bar_chart('["A", "B", "C"]', '[-4, -5, -6]')
        self.assertEqual(result, "Plot displayed.")

if __name__ == "__main__":
    unittest.main()
