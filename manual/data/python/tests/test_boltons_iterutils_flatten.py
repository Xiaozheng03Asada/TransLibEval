import unittest
from function_boltons_stringutils_clean import DataFlattener

class TestDataFlattener(unittest.TestCase):
    def setUp(self):
        self.function = DataFlattener()

    def test_flatten_data_valid_input(self):
        data_str = "1,2,3;4,5,6;7,8,9"
        result = self.function.flatten_data(data_str)
        expected = "1,2,3,4,5,6,7,8,9"
        self.assertEqual(result, expected)

    def test_flatten_data_empty_string(self):
        data_str = ""
        result = self.function.flatten_data(data_str)
        expected = "Error"
        self.assertEqual(result, expected)

    def test_flatten_data_invalid_input(self):
        data_str = 12345  # Invalid input type (not a string)
        result = self.function.flatten_data(data_str)
        expected = "Error"
        self.assertEqual(result, expected)

    def test_flatten_data_single_element(self):
        data_str = "1"
        result = self.function.flatten_data(data_str)
        expected = "1"
        self.assertEqual(result, expected)

    def test_flatten_data_mixed_structure(self):
        data_str = "1,2,3;4,5;6,7,8"
        result = self.function.flatten_data(data_str)
        expected = "1,2,3,4,5,6,7,8"
        self.assertEqual(result, expected)

if __name__ == "__main__":
    unittest.main()
