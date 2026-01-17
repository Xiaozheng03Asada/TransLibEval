import unittest
from function_Cerberus_Validator_schema import DataValidator

class TestDataValidator(unittest.TestCase):

    def setUp(self):
        self.validator = DataValidator()

    def test_valid_data(self):
        data_str = "{'name': 'John', 'age': 25}"
        result = self.validator.validate_data(data_str)
        self.assertEqual(result, "Valid data: {'name': {'type': 'string', 'minlength': 3}, 'age': {'type': 'integer', 'min': 18, 'max': 100}}")

    def test_invalid_name_length(self):
        data_str = "{'name': 'Jo', 'age': 25}"
        result = self.validator.validate_data(data_str)
        self.assertEqual(result, "Error: Invalid data - {'name': ['min length is 3']}")

    def test_invalid_age(self):
        data_str = "{'name': 'John', 'age': 17}"
        result = self.validator.validate_data(data_str)
        self.assertEqual(result, "Error: Invalid data - {'age': ['min value is 18']}")

    def test_non_string_input(self):
        data_str = "{'name': 123, 'age': 25}"
        result = self.validator.validate_data(data_str)
        self.assertEqual(result, "Error: Invalid data - {'name': ['must be of string type']}")

    def test_invalid_format(self):
        data_str = "{'name': 'Alice', 'age': 'invalid_age'}"
        result = self.validator.validate_data(data_str)
        self.assertEqual(result, "Error: Invalid data - {'age': ['must be of integer type']}")

if __name__ == '__main__':
    unittest.main()
