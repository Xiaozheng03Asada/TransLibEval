import unittest
from function_Polars_sum import PolarsExample

class TestPolarsExample(unittest.TestCase):

    def test_compute_sum_multiple_positive_numbers(self):
        calc = PolarsExample()
        result = calc.compute_sum(1, 2, 3)
        self.assertEqual(result, 6.0)

    def test_compute_sum_multiple_negative_numbers(self):
        calc = PolarsExample()
        result = calc.compute_sum(-1, -2, -3)
        self.assertEqual(result, -6.0)

    def test_compute_sum_mixed_numbers(self):
        calc = PolarsExample()
        result = calc.compute_sum(1, -1, 2)
        self.assertEqual(result, 2.0)

    def test_compute_sum_single_number(self):
        calc = PolarsExample()
        result = calc.compute_sum(10)
        self.assertEqual(result, 10.0)

    def test_compute_sum_no_input(self):
        calc = PolarsExample()
        result = calc.compute_sum(10, None, None)
        self.assertEqual(result, 10.0)  # Only 10 should be considered

if __name__ == '__main__':
    unittest.main()
