import unittest
import numpy as np
from function_cvxpy_sum_squares import CVXPYSumOfSquaresFunction

class TestCVXPYSumOfSquaresFunction(unittest.TestCase):

    def test_sum_of_squares_computation(self):
        vector_values_str = "1,-2,3"
        result = CVXPYSumOfSquaresFunction().solve_sum_of_squares(vector_values_str)
        expected_value = np.sum(np.square([1, -2, 3]))
        self.assertAlmostEqual(float(result), expected_value, places=10)

    def test_floating_point_precision(self):
        vector_values_str = "1.0000000001,2.0000000002,3.0000000003"
        result = CVXPYSumOfSquaresFunction().solve_sum_of_squares(vector_values_str)
        expected_value = np.sum(np.square([1.0000000001, 2.0000000002, 3.0000000003]))
        self.assertAlmostEqual(float(result), expected_value, places=10)
        
    def test_single_element_vector(self):
        vector_values_str = "5"
        result = CVXPYSumOfSquaresFunction().solve_sum_of_squares(vector_values_str)
        self.assertEqual(result, "25.0")

    def test_small_values_in_vector(self):
        vector_values_str = "1e-6,2e-6,3e-6"
        result = CVXPYSumOfSquaresFunction().solve_sum_of_squares(vector_values_str)
        expected_value = np.sum(np.square([1e-6, 2e-6, 3e-6]))
        self.assertAlmostEqual(float(result), expected_value, places=10)

    def test_negative_input(self):
        vector_values_str = "-1,-4,-2"
        result = CVXPYSumOfSquaresFunction().solve_sum_of_squares(vector_values_str)
        expected_value = np.sum(np.square([-1, -4, -2]))
        self.assertAlmostEqual(float(result), expected_value, places=10)

if __name__ == "__main__":
    unittest.main()
