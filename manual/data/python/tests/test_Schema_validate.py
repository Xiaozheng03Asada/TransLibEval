import unittest
from function_Schema_validate import PersonValidator

class TestPersonValidator(unittest.TestCase):

    def test_valid_data(self):
        validator = PersonValidator()
        result = validator.validate("Alice", 30, "New York")
        self.assertEqual(result, "Valid data: name = Alice, age = 30, city = New York")

    def test_invalid_age(self):
        validator = PersonValidator()
        result = validator.validate("Alice", 15, "New York")
        self.assertEqual(result, "Invalid data: One or more fields are incorrect")

    def test_invalid_city(self):
        validator = PersonValidator()
        result = validator.validate("Bob", "25", 12345)
        self.assertEqual(result, "Invalid data: One or more fields are incorrect")

    def test_missing_city(self):
        validator = PersonValidator()
        result = validator.validate("Charlie", "28", "")
        self.assertEqual(result, "Invalid data: One or more fields are incorrect")

    def test_invalid_name(self):
        validator = PersonValidator()
        result = validator.validate("", "45", "London")
        self.assertEqual(result, "Invalid data: One or more fields are incorrect")

if __name__ == "__main__":
    unittest.main()
