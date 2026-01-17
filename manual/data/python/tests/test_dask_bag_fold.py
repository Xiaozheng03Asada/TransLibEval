import unittest
from function_dask_bag_fold import CumulativeSumFunction

class TestCumulativeSumFunction(unittest.TestCase):

    def setUp(self):
        self.cumsum_func = CumulativeSumFunction()

    def test_basic_sum(self):
        result = self.cumsum_func.cumulative_sum("1,2,3,4,5")
        self.assertEqual(result, "15.0")

    def test_negative_numbers(self):
        result = self.cumsum_func.cumulative_sum("10,-2,-3,5")
        self.assertEqual(result, "10.0")

    def test_alternating_signs(self):
        result = self.cumsum_func.cumulative_sum("1,-1,2,-2,3,-3")
        self.assertEqual(result, "0.0")

    def test_large_numbers(self):
        result = self.cumsum_func.cumulative_sum("1000000,1000000,1000000")
        self.assertEqual(result, "3000000.0")

    def test_invalid_input(self):
        result = self.cumsum_func.cumulative_sum("1,a,None,4")
        self.assertEqual(result, "Error")

if __name__ == '__main__':
    unittest.main()
