import unittest
from function_joblib_hashing_hash import HashingExample

class TestHashingExample(unittest.TestCase):

    def test_compute_hash_string(self):
        calc = HashingExample()
        result = calc.compute_hash("apple")
        self.assertIsInstance(result, str)  # Ensure result is a string

    def test_compute_hash_integer(self):
        calc = HashingExample()
        result = calc.compute_hash(123)
        self.assertIsInstance(result, str)  # Ensure result is a string

    def test_empty_input(self):
        calc = HashingExample()
        result = calc.compute_hash("")
        self.assertIsInstance(result, str)  # Ensure result is a string

    def test_non_string_input(self):
        calc = HashingExample()
        result = calc.compute_hash(456.789)
        self.assertIsInstance(result, str)  # Ensure result is a string

    def test_edge_case(self):
        calc = HashingExample()
        result = calc.compute_hash("apple apple apple")
        self.assertIsInstance(result, str)  # Ensure result is a string

if __name__ == '__main__':
    unittest.main()
