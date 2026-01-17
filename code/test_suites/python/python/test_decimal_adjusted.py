import unittest
from function_decimal_adjusted import GetDecimalAdjusted

class TestGetDecimalAdjusted(unittest.TestCase):

    def setUp(self):
        self.get_decimal_adjusted_func = GetDecimalAdjusted()

    def test_large_value(self):
        result = self.get_decimal_adjusted_func.check_discount_for_large_order(12345.6789)
        self.assertEqual(result, "4")

    def test_small_value(self):
        result = self.get_decimal_adjusted_func.check_discount_for_large_order(0.00012345)
        self.assertEqual(result, "-4")

    def test_no_decimal(self):
        result = self.get_decimal_adjusted_func.check_discount_for_large_order(12345)
        self.assertEqual(result, "4")

    def test_large_negative_value(self):
        result = self.get_decimal_adjusted_func.check_discount_for_large_order(-9876.54321)
        self.assertEqual(result, "3")

    def test_large_negative_range_value(self):
        result = self.get_decimal_adjusted_func.check_discount_for_large_order(-1e+100)
        self.assertEqual(result, "100")

if __name__ == "__main__":
    unittest.main()
