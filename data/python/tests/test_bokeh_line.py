import unittest
from function_bokeh_line import Plotter

class TestBokehLinePlot(unittest.TestCase):
    
    def test_line_plot_creation(self):
        plotter = Plotter()
        result = plotter.create_line_plot("[1, 2, 3]", "[4, 5, 6]")
        self.assertEqual(result, "Plot displayed.")
    
    def test_empty_input(self):
        plotter = Plotter()
        result = plotter.create_line_plot("[]", "[]")
        self.assertEqual(result, "Error: Invalid input format.")
    
    def test_invalid_length(self):
        plotter = Plotter()
        result = plotter.create_line_plot("[1, 2]", "[3]")
        self.assertEqual(result, "Error: x_values and y_values must have the same length.")
    
    def test_custom_title(self):
        plotter = Plotter()
        result = plotter.create_line_plot("[1, 2, 3]", "[4, 5, 6]", "Custom Line Plot Title")
        self.assertEqual(result, "Plot displayed.")
    
    def test_negative_values(self):
        plotter = Plotter()
        result = plotter.create_line_plot("[-1, -2, -3]", "[-4, -5, -6]")
        self.assertEqual(result, "Plot displayed.")
        
if __name__ == '__main__':
    unittest.main()
