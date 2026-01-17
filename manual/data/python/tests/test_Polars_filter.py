import unittest
from function_Polars_filter import PolarsExample

class TestPolarsExample(unittest.TestCase):

    def test_filter_numbers_greater_than_threshold(self):
        calc = PolarsExample()
        result = calc.filter_numbers(2, 1, 2, 3)
        self.assertEqual(result, 3.0)

    def test_filter_numbers_multiple_values(self):
        calc = PolarsExample()
        result = calc.filter_numbers(1, 5, 2, 7)
        self.assertEqual(result, 7.0)

    def test_filter_numbers_empty_result(self):
        calc = PolarsExample()
        result = calc.filter_numbers(10, 1, 2, 3)
        self.assertIsNone(result)

    def test_filter_numbers_single_value_greater_than_threshold(self):
        calc = PolarsExample()
        result = calc.filter_numbers(0, 1)
        self.assertEqual(result, 1.0)

    def test_filter_numbers_no_values_above_threshold(self):
        calc = PolarsExample()
        result = calc.filter_numbers(100, 10, 20, 30)
        self.assertIsNone(result)

if __name__ == '__main__':
    unittest.main()
