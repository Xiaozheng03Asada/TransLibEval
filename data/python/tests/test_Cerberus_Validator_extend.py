import unittest
from function_Cerberus_Validator_extend import SimpleValidator

class TestSimpleValidator(unittest.TestCase):

    def setUp(self):
        self.schema_str = "{'age': {'type': 'integer', 'min': 18, 'max': 100}, 'name': {'type': 'string', 'maxlength': 50}}"
        self.validator = SimpleValidator()

    def test_valid_data(self):
        data_str = "{'age': 25, 'name': 'John Doe'}"
        result = self.validator.validate_data(data_str, self.schema_str)
        self.assertEqual(result, "Valid data")

    def test_invalid_age(self):
        data_str = "{'age': 17, 'name': 'John Doe'}"
        result = self.validator.validate_data(data_str, self.schema_str)
        self.assertEqual(result, "Invalid data: {'age': ['min value is 18']}")

    def test_invalid_name_length(self):
        data_str = "{'age': 30, 'name': 'a' * 51}"
        result = self.validator.validate_data(data_str, self.schema_str)
        self.assertEqual(result, "Invalid data: {'name': ['max length is 50']}")

    def test_invalid_name_type(self):
        data_str = "{'age': 25, 'name': 12345}"
        result = self.validator.validate_data(data_str, self.schema_str)
        self.assertEqual(result, "Invalid data: {'name': ['must be of string type']}")

    def test_multiple_errors(self):
        data_str = "{'age': 17, 'name': 'a' * 51}"
        result = self.validator.validate_data(data_str, self.schema_str)
        self.assertEqual(result, "Invalid data: {'age': ['min value is 18'], 'name': ['max length is 50']}")

if __name__ == '__main__':
    unittest.main()
