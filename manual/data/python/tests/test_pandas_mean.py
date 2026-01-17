import unittest
from function_pandas_mean import CalculateMean

class TestCalculateMean(unittest.TestCase):

    def test_success(self):
        calc_mean = CalculateMean()
        result = calc_mean.calculate_mean(1, 2)
        self.assertTrue(result.startswith("A:"))

    def test_mean_values(self):
        calc_mean = CalculateMean()
        result = calc_mean.calculate_mean(1, 2)
        if result.startswith("A:"):
            mean_values_str = result.split(", ")
            mean_A = float(mean_values_str[0].split(":")[1].strip())
            mean_B = float(mean_values_str[1].split(":")[1].strip())
            self.assertAlmostEqual(mean_A, 1.0, delta=0.1)
            self.assertAlmostEqual(mean_B, 2.0, delta=0.1)

    def test_result_format(self):
        calc_mean = CalculateMean()
        result = calc_mean.calculate_mean(1, 2)
        self.assertIsInstance(result, str)

    def test_empty_data(self):
        calc_mean = CalculateMean()
        result = calc_mean.calculate_mean(None, None)
        self.assertTrue("A: None" in result)
        self.assertTrue("B: None" in result)

    def test_column_consistency(self):
        calc_mean = CalculateMean()
        result = calc_mean.calculate_mean(1, 2)
        self.assertIn("A:", result)
        self.assertIn("B:", result)

if __name__ == '__main__':
    unittest.main()
