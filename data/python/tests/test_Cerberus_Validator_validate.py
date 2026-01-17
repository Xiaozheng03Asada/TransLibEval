import unittest
from function_Cerberus_Validator_validate import NumberComparer

class TestNumberComparer(unittest.TestCase):

    def setUp(self):
        self.comparer = NumberComparer()

    def test_greater_numbers(self):
        result = self.comparer.compare_numbers(10, 5)
        self.assertEqual(result, "Greater")

    def test_smaller_numbers(self):
        result = self.comparer.compare_numbers(5, 10)
        self.assertEqual(result, "Smaller")

    def test_equal_numbers(self):
        result = self.comparer.compare_numbers(10, 10)
        self.assertEqual(result, "Equal")

    def test_non_numeric_input(self):
        result = self.comparer.compare_numbers("a", 5)
        self.assertEqual(result, "Error: Invalid input. {'num1': ['must be of number type']}")

    def test_non_numeric_input2(self):
        result = self.comparer.compare_numbers(10, "b")
        self.assertEqual(result, "Error: Invalid input. {'num2': ['must be of number type']}")

if __name__ == '__main__':
    unittest.main()
