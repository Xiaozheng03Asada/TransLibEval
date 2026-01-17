import unittest
from function_Cerberus_Validator_normalized import DataNormalizer

class TestNormalizedData(unittest.TestCase):

    def setUp(self):
        self.normalizer = DataNormalizer()

    def test_data_with_missing_fields(self):
        data_str = "{'name': 'Alice'}"
        normalized_data = self.normalizer.normalize_data(data_str)
        self.assertEqual(normalized_data, "{'name': 'Alice', 'age': 18}")

    def test_data_with_all_fields(self):
        data_str = "{'name': 'Bob', 'age': 25}"
        normalized_data = self.normalizer.normalize_data(data_str)
        self.assertEqual(normalized_data, "{'name': 'Bob', 'age': 25}")

    def test_data_with_missing_age(self):
        data_str = "{'name': 'Charlie'}"
        normalized_data = self.normalizer.normalize_data(data_str)
        self.assertEqual(normalized_data, "{'name': 'Charlie', 'age': 18}")

    def test_empty_data(self):
        data_str = "{}"
        normalized_data = self.normalizer.normalize_data(data_str)
        self.assertEqual(normalized_data, "{'name': 'Unknown', 'age': 18}")

    def test_invalid_input(self):
        data_str = 12345  # Invalid input type (not a string)
        normalized_data = self.normalizer.normalize_data(data_str)
        self.assertEqual(normalized_data, "Error: Input must be a string")


if __name__ == '__main__':
    unittest.main()
