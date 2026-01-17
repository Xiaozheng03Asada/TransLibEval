import unittest
from function_decimal_quantize import SetDecimalPrecision

class TestDecimalPrecision(unittest.TestCase):

    def setUp(self):
        self.set_decimal_precision_func = SetDecimalPrecision()

    def test_round_to_three_decimal_places(self):
        result = self.set_decimal_precision_func.check_discount_for_large_order("2.71828", 3)
        self.assertEqual(result, "2.718")

    def test_round_up(self):
        result = self.set_decimal_precision_func.check_discount_for_large_order("1.2345", 2)
        self.assertEqual(result, "1.23")

    def test_round_down(self):
        result = self.set_decimal_precision_func.check_discount_for_large_order("0.99999", 3)
        self.assertEqual(result, "1.000")

    def test_invalid_value(self):
        result = self.set_decimal_precision_func.check_discount_for_large_order("invalid", 2)
        self.assertEqual(result, "Error")

    def test_negative_number(self):
        result = self.set_decimal_precision_func.check_discount_for_large_order("-5.6789", 2)
        self.assertEqual(result, "-5.68")

if __name__ == "__main__":
    unittest.main()
