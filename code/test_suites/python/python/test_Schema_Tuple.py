import unittest
from function_Schema_Tuple import ValidateTuple

class TestValidateTuple(unittest.TestCase):

    def test_valid_tuple(self):
        validator = ValidateTuple()
        result = validator.validate("42, hello")
        self.assertEqual(result, "Valid tuple: (42, hello)")

    def test_invalid_second_element(self):
        validator = ValidateTuple()
        result = validator.validate("42, 100")
        self.assertEqual(result, "Valid tuple: (42, 100)")

    def test_valid_other_tuple(self):
        validator = ValidateTuple()
        result = validator.validate("100, world")
        self.assertEqual(result, "Valid tuple: (100, world)")

    def test_invalid_first_element(self):
        validator = ValidateTuple()
        result = validator.validate("hello, 42")
        self.assertEqual(result, "Invalid input: First value must be an integer")

    def test_not_a_pair(self):
        validator = ValidateTuple()
        result = validator.validate("42")
        self.assertEqual(result, "Invalid input: Must be a pair of values")

if __name__ == "__main__":
    unittest.main()
