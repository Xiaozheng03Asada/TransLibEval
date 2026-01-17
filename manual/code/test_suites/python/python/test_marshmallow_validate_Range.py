import unittest
from function_marshmallow_validate_Range import UserValidator

class TestUserValidator(unittest.TestCase):

    def setUp(self):
        self.validator = UserValidator()

    def test_valid_data(self):
        user_data = '{"age": 25, "score": 85.5}'
        result = self.validator.validate_user_data(user_data)
        self.assertIn('"age": 25', result)
        self.assertIn('"score": 85.5', result)

    def test_invalid_age_too_low_or_high(self):
        user_data = '{"age": -1, "score": 85.5}'
        result = self.validator.validate_user_data(user_data)
        self.assertEqual(result, "Error: invalid input")

        user_data = '{"age": 150, "score": 85.5}'
        result = self.validator.validate_user_data(user_data)
        self.assertEqual(result, "Error: invalid input")

    def test_invalid_score_too_low_or_high(self):
        user_data = '{"age": 25, "score": -5.0}'
        result = self.validator.validate_user_data(user_data)
        self.assertEqual(result, "Error: invalid input")

        user_data = '{"age": 25, "score": 105.0}'
        result = self.validator.validate_user_data(user_data)
        self.assertEqual(result, "Error: invalid input")

    def test_additional_unexpected_field(self):
        user_data = '{"age": 25, "score": 85.0, "name": "John Doe"}'
        result = self.validator.validate_user_data(user_data)
        self.assertEqual(result, "Error: invalid input")

    def test_both_fields_missing(self):
        user_data = '{}'
        result = self.validator.validate_user_data(user_data)
        self.assertEqual(result, "Error: invalid input")

if __name__ == '__main__':
    unittest.main()
