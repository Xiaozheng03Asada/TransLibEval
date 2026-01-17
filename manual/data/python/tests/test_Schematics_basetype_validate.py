import unittest
from function_Schematics_basetype_validate import PositiveIntegerType

class TestPositiveIntegerType(unittest.TestCase):

    def test_valid_positive_integer(self):
        field = PositiveIntegerType()
        input_value = "5"  
        result = field.validate(input_value)
        self.assertEqual(result, "Validation successful.")

    def test_invalid_non_integer(self):
        field = PositiveIntegerType()
        result = field.validate("not_an_integer")
        self.assertEqual(result, "Value must be an integer.")

    def test_invalid_negative_integer(self):
        field = PositiveIntegerType()
        result = field.validate("-10")
        self.assertEqual(result, "Value must be a positive integer.")

    def test_invalid_float(self):
        field = PositiveIntegerType()
        result = field.validate("10.5")
        self.assertEqual(result, "Value must be an integer.")

    def test_valid_integer_type_conversion(self):
        field = PositiveIntegerType()
        input_value = "10"  # 输入为字符串
        result = field.validate(input_value)
        self.assertEqual(result, "Validation successful.")

if __name__ == "__main__":
    unittest.main()
