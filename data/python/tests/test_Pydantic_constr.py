import unittest
from function_Pydantic_constr import UserProfileHandler

class TestCreateUserProfile(unittest.TestCase):
    def test_valid_profile(self):
        handler = UserProfileHandler()
        result = handler.create_user_profile("user_123", "user@example.com")
        self.assertEqual(result, "username='user_123' email='user@example.com'")

    def test_invalid_email(self):
        handler = UserProfileHandler()
        result = handler.create_user_profile("user123", "user.com")
        self.assertIsInstance(result, str)
        self.assertIn('value is not a valid email address', result)

    def test_valid_profile_with_min_length_username(self):
        handler = UserProfileHandler()
        result = handler.create_user_profile("abc", "test@example.com")
        self.assertEqual(result, "username='abc' email='test@example.com'")

    def test_valid_profile_with_max_length_username(self):
        handler = UserProfileHandler()
        result = handler.create_user_profile("a" * 20, "longusername@example.com")
        self.assertEqual(result, "username='aaaaaaaaaaaaaaaaaaaa' email='longusername@example.com'")

    def test_invalid_email_missing_domain(self):
        handler = UserProfileHandler()
        result = handler.create_user_profile("user123", "user@")
        self.assertIsInstance(result, str)
        self.assertIn('value is not a valid email address', result)

if __name__ == "__main__":
    unittest.main()
