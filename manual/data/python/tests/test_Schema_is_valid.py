import unittest
from function_Schema_is_valid import UserValidator

class TestUserValidator(unittest.TestCase):

    def setUp(self):
        self.validator = UserValidator()

    def test_valid_data(self):
        result = self.validator.validate_user("Alice", "25", "alice@example.com")
        self.assertEqual(result, "Valid data")

    def test_missing_optional_field(self):
        result = self.validator.validate_user("Bob", "30")
        self.assertEqual(result, "Valid data")

    def test_invalid_email_format(self):
        result = self.validator.validate_user("Diana", "22", "invalid_email")
        self.assertEqual(result, "Invalid data")

    def test_additional_unexpected_field(self):
        result = self.validator.validate_user("Eve", "30", "extra_field")
        self.assertEqual(result, "Invalid data")

    def test_null_email(self):
        result = self.validator.validate_user("Frank", "40", None)
        self.assertEqual(result, "Valid data")

if __name__ == "__main__":
    unittest.main()
