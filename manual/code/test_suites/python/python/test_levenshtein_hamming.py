import unittest
from function_levenshtein_hamming import StringProcessor

class TestHammingDistance(unittest.TestCase):

    def setUp(self):
        self.processor = StringProcessor()

    def test_equal_strings(self):
        result = self.processor.calculate_hamming_distance("test", "test")
        self.assertEqual(result, 0)

    def test_single_character_difference(self):
        result = self.processor.calculate_hamming_distance("objective", "objection")
        self.assertEqual(result, 2)

    def test_insertions_and_deletions(self):
        result = self.processor.calculate_hamming_distance("hello", "helo")
        self.assertEqual(result, "Strings must be of the same length for Hamming distance.")


    def test_single_substitution(self):
        result = self.processor.calculate_hamming_distance("flaw", "lawn")
        self.assertEqual(result, 4)  

    def test_non_string_input(self):
        result = self.processor.calculate_hamming_distance(123, "hello")
        self.assertEqual(result,"Error: Both inputs must be strings")

if __name__ == "__main__":
    unittest.main()
