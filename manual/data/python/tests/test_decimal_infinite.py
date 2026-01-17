import unittest
from function_decimal_infinite import InfiniteCheck

class TestInfiniteFunction(unittest.TestCase):
    
    def setUp(self):
        self.infinite_check_func = InfiniteCheck()

    def test_positive_infinity(self):
        value = "Infinity"
        self.assertEqual(self.infinite_check_func.check_discount_for_large_order(value), "True")

    def test_negative_infinity(self):
        value = "-Infinity"
        self.assertEqual(self.infinite_check_func.check_discount_for_large_order(value), "True")

    def test_zero(self):
        value = "0.0"
        self.assertEqual(self.infinite_check_func.check_discount_for_large_order(value), "False")

    def test_finite_number(self):
        value = "10.5"
        self.assertEqual(self.infinite_check_func.check_discount_for_large_order(value), "False")

    def test_nan(self):
        value = "NaN"
        self.assertEqual(self.infinite_check_func.check_discount_for_large_order(value), "False")

if __name__ == '__main__':
    unittest.main()
