import unittest
from function_sklearn_OneHotEncoder import OneHotEncoderFunction

class TestOneHotEncodingFunction(unittest.TestCase):

    def test_function_call(self):
        data = "category1 5"
        try:
            result = OneHotEncoderFunction.test_one_hot_encoding(data)
            self.assertEqual(type(result), str)
        except Exception as e:
            self.fail(f"Function call failed with exception: {e}")

    def test_correct_data(self):
        result = OneHotEncoderFunction.test_one_hot_encoding("category1 5")
        self.assertEqual(type(result), str)

    def test_encoded_data(self):
        result = OneHotEncoderFunction.test_one_hot_encoding("category1 5")
        result_split = result.split(",")
        self.assertEqual(result_split[0], "5")
        self.assertEqual(result_split[1], "1.0")

    def test_invalid_data_type(self):
        with self.assertRaises(TypeError):
            invalid_data = 12345
            OneHotEncoderFunction.test_one_hot_encoding(invalid_data)

    def test_missing_categorical_data(self):
        with self.assertRaises(IndexError):
            incomplete_data = "12"
            OneHotEncoderFunction.test_one_hot_encoding(incomplete_data)

if __name__ == '__main__':
    unittest.main()
