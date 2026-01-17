import unittest
from function_Pydantic_basemodel import UserValidator

class TestCreateUser(unittest.TestCase):

    def test_valid_user(self):
        validator = UserValidator()
        result = validator.create_user("John Doe", 30, "john@example.com")
        self.assertEqual(result, "name='John Doe' age=30 email='john@example.com'")

    def test_invalid_age(self):
        validator = UserValidator()
        result = validator.create_user("John Doe", "thirty", "john@example.com")
        self.assertIn('age', result)

    def test_invalid_email(self):
        validator = UserValidator()
        result = validator.create_user("John Doe", 30, "invalid-email")
        self.assertIn('email', result)

    def test_missing_field(self):
        validator = UserValidator()
        result = validator.create_user("John Doe", 30, "")
        self.assertIn('email', result)

    def test_invalid_email_type(self):
        validator = UserValidator()
        result = validator.create_user("John Doe", 30, 12345)
        self.assertIsInstance(result, str)
        self.assertIn('email', result)

if __name__ == "__main__":
    unittest.main()
