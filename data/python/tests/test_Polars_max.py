import unittest
from function_Polars_max import PolarsExample

class TestPolarsExample(unittest.TestCase):

    def test_compute_max_multiple_positive_numbers(self):
        calc = PolarsExample()
        result = calc.compute_max(1, 2, 3)
        self.assertEqual(result, 3.0)

    def test_compute_max_multiple_negative_numbers(self):
        calc = PolarsExample()
        result = calc.compute_max(-1, -2, -3)
        self.assertEqual(result, -1.0)

    def test_compute_max_mixed_numbers(self):
        calc = PolarsExample()
        result = calc.compute_max(1, -1, 2)
        self.assertEqual(result, 2.0)

    def test_compute_max_single_number(self):
        calc = PolarsExample()
        result = calc.compute_max(10)
        self.assertEqual(result, 10.0)

    def test_compute_max_no_input(self):
        calc = PolarsExample()
        result = calc.compute_max(10, None, None)
        self.assertEqual(result, 10.0)

if __name__ == '__main__':
    unittest.main()
