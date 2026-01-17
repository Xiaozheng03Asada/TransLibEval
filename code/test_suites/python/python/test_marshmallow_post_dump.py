import unittest
from function_marshmallow_post_dump import UserSchema

class TestSerializeUserData(unittest.TestCase):

    def setUp(self):
        self.schema = UserSchema()

    def test_valid_user_data(self):
        user_data = '{"age": 30, "score": 85.0}'
        result = self.schema.serialize_user_data(user_data)
        self.assertIn('"result":', result)
        self.assertIn('"age": 30', result)
        self.assertIn('"score": 85.0', result)

    def test_output_structure(self):
        user_data = '{"age": 50, "score": 95.5}'
        result = self.schema.serialize_user_data(user_data)
        self.assertIn('"result":', result)

    def test_extra_fields_in_input(self):
        user_data = '{"age": 35, "score": 88.5, "extra": "field"}'
        result = self.schema.serialize_user_data(user_data)
        self.assertIn('"result":', result)
        self.assertIn('"age": 35', result)
        self.assertIn('"score": 88.5', result)
        self.assertNotIn('"extra":', result)

    def test_zero_values(self):
        user_data = '{"age": 0, "score": 0.0}'
        result = self.schema.serialize_user_data(user_data)
        self.assertIn('"age": 0', result)
        self.assertIn('"score": 0.0', result)

    def test_negative_numbers(self):
        user_data = '{"age": -25, "score": -5.5}'
        result = self.schema.serialize_user_data(user_data)
        self.assertIn('"age": -25', result)
        self.assertIn('"score": -5.5', result)

if __name__ == '__main__':
    unittest.main()
