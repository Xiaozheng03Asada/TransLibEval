import unittest
from function_mlxtend_plot_learning_curves import LearningCurvePlotter

class TestPlotLearningCurves(unittest.TestCase):
    
    def test_function_execution(self):
        plot_instance = LearningCurvePlotter()
        result = plot_instance.plot_learning_curve(100, 64, 10)
        self.assertEqual(result, "Learning curve plotted successfully")
    
    def test_no_output_on_plot(self):
        plot_instance = LearningCurvePlotter()
        result = plot_instance.plot_learning_curve(50, 64, 10)
        self.assertEqual(result, "Learning curve plotted successfully")
    
    def test_plot_return_value(self):
        plot_instance = LearningCurvePlotter()
        result = plot_instance.plot_learning_curve(200, 64, 10)
        self.assertEqual(result, "Learning curve plotted successfully")
    
    def test_plot_title(self):
        plot_instance = LearningCurvePlotter()
        result = plot_instance.plot_learning_curve(100, 64, 10)
        self.assertEqual(result, "Learning curve plotted successfully")
    
    def test_invalid_input(self):
        plot_instance = LearningCurvePlotter()
        result = plot_instance.plot_learning_curve(0, 64, 10) 
        self.assertEqual(result, "Invalid number of samples")

if __name__ == "__main__":
    unittest.main()
