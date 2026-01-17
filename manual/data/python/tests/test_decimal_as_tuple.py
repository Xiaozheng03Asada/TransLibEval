import unittest
from function_decimal_as_tuple import GetDecimalTuple

class TestGetDecimalTuple(unittest.TestCase):

    def setUp(self):
        self.get_decimal_tuple_func = GetDecimalTuple()

    def test_positive_integer(self):
        result = self.get_decimal_tuple_func.check_discount_for_large_order("12345")
        self.assertEqual(result, "0,12345,0")

    def test_negative_integer(self):
        result = self.get_decimal_tuple_func.check_discount_for_large_order("-12345")
        self.assertEqual(result, "1,12345,0")

    def test_positive_decimal(self):
        result = self.get_decimal_tuple_func.check_discount_for_large_order("123.45")
        self.assertEqual(result, "0,12345,-2")

    def test_negative_decimal(self):
        result = self.get_decimal_tuple_func.check_discount_for_large_order("-123.45")
        self.assertEqual(result, "1,12345,-2")

    def test_zero_value(self):
        result = self.get_decimal_tuple_func.check_discount_for_large_order("0")
        self.assertEqual(result, "0,0,0")

if __name__ == "__main__":
    unittest.main()
