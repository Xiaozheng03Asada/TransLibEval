import unittest
from function_sklearn_mean_squared_error import MeanSquaredErrorCalculator

class TestMeanSquaredErrorCalculator(unittest.TestCase):
    
    def test_perfect_fit(self):
        X = "1,2,3"
        y = "2,4,6"
        calculator = MeanSquaredErrorCalculator()
        result = calculator.calculate(X, y)
        self.assertAlmostEqual(float(result), 0.0, places=6)

    def test_non_linear_data(self):
        X = "1,2,3,4"
        y = "1,4,9,16" 
        calculator = MeanSquaredErrorCalculator()
        result = calculator.calculate(X, y)
        self.assertTrue(float(result) > 0.0) 

    def test_random_data_shape_check(self):
        X = "0.1,0.2,0.3,0.4,0.5"
        y = "0.2,0.4,0.6,0.8,1.0"
        calculator = MeanSquaredErrorCalculator()
        result = calculator.calculate(X, y)
        self.assertTrue(float(result) > 0.0) 

    def test_random_data_value(self):
        X = "0.2,0.4,0.6,0.8,1.0"
        y = "0.1,0.3,0.5,0.7,0.9"
        calculator = MeanSquaredErrorCalculator()
        result = calculator.calculate(X, y)
        self.assertTrue(float(result) >= 0.0)

    def test_incompatible_shapes(self):
        X = "1,2"
        y = "1,2,3"
        with self.assertRaises(ValueError):
            calculator = MeanSquaredErrorCalculator()
            calculator.calculate(X, y)

if __name__ == '__main__':
    unittest.main()
