import unittest
from function_dask_array_mean import CalculateMeanFunction

class TestCalculateBlockwiseMean(unittest.TestCase):

    def setUp(self):
        self.mean_func = CalculateMeanFunction()

    def test_basic_array(self):
        result = self.mean_func.calculate_mean("1,2,3,4", 2)
        self.assertEqual(result, "2.5")

    def test_floating_point_array(self):
        result = self.mean_func.calculate_mean("1.5,2.5,3.5,4.5", 2)
        self.assertEqual(result, "3.0")

    def test_high_dimensional_array(self):
        result = self.mean_func.calculate_mean("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24", 4)
        self.assertEqual(result, "12.5")

    def test_non_numeric_data(self):
        result = self.mean_func.calculate_mean("a,b,c", 2)
        self.assertTrue(result.startswith("Error:"))

    def test_empty_string(self):
        result = self.mean_func.calculate_mean("", 2)
        self.assertTrue(result.startswith("Error:"))

if __name__ == '__main__':
    unittest.main()
