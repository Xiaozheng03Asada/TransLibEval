import unittest
from function_Schema_And import StringValidator

class TestStringValidator(unittest.TestCase):

    def setUp(self):
        self.validator = StringValidator()

    def test_valid_data(self):
        result = self.validator.validate_user_input("admin", "securepassword123")
        self.assertEqual(result, "Validation passed: username = admin, password = securepassword123")

    def test_missing_username(self):
        result = self.validator.validate_user_input("", "securepassword123")
        self.assertEqual(result, "Validation failed")

    def test_short_password(self):
        result = self.validator.validate_user_input("admin", "short")
        self.assertEqual(result, "Validation failed")

    def test_non_string_username(self):
        result = self.validator.validate_user_input(12345, "securepassword123")
        self.assertEqual(result, "Validation failed")

    def test_valid_data_no_errors(self):
        result = self.validator.validate_user_input("admin", "securepass123")
        self.assertEqual(result, "Validation passed: username = admin, password = securepass123")

if __name__ == '__main__':
    unittest.main()
