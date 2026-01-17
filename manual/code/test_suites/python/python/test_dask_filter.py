import unittest
from function_dask_filter import FilterEvenNumbersDask

class TestFilterEvenNumbersDask(unittest.TestCase):

    def setUp(self):
        self.filter_func = FilterEvenNumbersDask()

    def test_mixed_numbers(self):
        data = "1,2,3,4,5"
        result = self.filter_func.check_discount_for_large_order(data)
        self.assertEqual(result, "2,4")

    def test_negative_numbers(self):
        data = "-10,-9,-8,-7,-6,-5,-4,-3,-2,-1"
        result = self.filter_func.check_discount_for_large_order(data)
        self.assertEqual(result, "-10,-8,-6,-4,-2")

    def test_invalid_format(self):
        data = "2,4,a,6"
        result = self.filter_func.check_discount_for_large_order(data)
        self.assertEqual(result, "Error")

    def test_large_dataset(self):
        data = ",".join(map(str, range(1, 10001)))
        result = self.filter_func.check_discount_for_large_order(data)
        self.assertEqual(result, ",".join(map(str, range(2, 10001, 2))))

    def test_empty_input(self):
        data = ""
        result = self.filter_func.check_discount_for_large_order(data)
        self.assertEqual(result, "")

if __name__ == "__main__":
    unittest.main()
