import unittest
from function_seaborn_kdeplot import SeabornKDEPlot

class TestSeabornKDEPlot(unittest.TestCase):

    def test_kdeplot_single_data(self):
        data = "1.0,2.0,3.0,4.0,5.0"
        kde_plot = SeabornKDEPlot()
        result = kde_plot.generate_kdeplot(data, shade=True, color='blue')
        self.assertIn("'data': '1.0,2.0,3.0,4.0,5.0'", result)
        self.assertIn("'shade': True", result)
        self.assertIn("'color': 'blue'", result)

    def test_kdeplot_with_different_color(self):
        data = "1.0,2.0,3.0,4.0,5.0"
        kde_plot = SeabornKDEPlot()
        result = kde_plot.generate_kdeplot(data, shade=True, color='red')
        self.assertIn("'data': '1.0,2.0,3.0,4.0,5.0'", result)
        self.assertIn("'shade': True", result)
        self.assertIn("'color': 'red'", result)

    def test_kdeplot_without_shade(self):
        data = "1.0,2.0,3.0,4.0,5.0"
        kde_plot = SeabornKDEPlot()
        result = kde_plot.generate_kdeplot(data, shade=False, color='green')
        self.assertIn("'data': '1.0,2.0,3.0,4.0,5.0'", result)
        self.assertIn("'shade': False", result)
        self.assertIn("'color': 'green'", result)

    def test_kdeplot_empty_data(self):
        data = ""
        kde_plot = SeabornKDEPlot()
        with self.assertRaises(ValueError):
            kde_plot.generate_kdeplot(data, shade=True, color='blue')

    def test_kdeplot_invalid_data(self):
        data = "invalid data"
        kde_plot = SeabornKDEPlot()
        with self.assertRaises(TypeError):
            kde_plot.generate_kdeplot(data, shade=True, color='blue')

if __name__ == "__main__":
    unittest.main()
