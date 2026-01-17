import unittest
from function_Polars_mean import PolarsExample

class TestPolarsExample(unittest.TestCase):

    def test_compute_mean_multiple_positive_numbers(self):
        calc = PolarsExample()
        result = calc.compute_mean(1, 2, 3)
        self.assertEqual(result, 2.0)

    def test_compute_mean_multiple_negative_numbers(self):
        calc = PolarsExample()
        result = calc.compute_mean(-1, -2, -3)
        self.assertEqual(result, -2.0)

    def test_compute_mean_mixed_numbers(self):
        calc = PolarsExample()
        result = calc.compute_mean(1, -1, 2)
        self.assertEqual(result, 0.6666666666666666)

    def test_compute_mean_single_number(self):
        calc = PolarsExample()
        result = calc.compute_mean(10)
        self.assertEqual(result, 10.0)

    def test_compute_mean_no_input(self):
        calc = PolarsExample()
        result = calc.compute_mean(10, None, None)
        self.assertEqual(result, 10.0)

if __name__ == '__main__':
    unittest.main()
