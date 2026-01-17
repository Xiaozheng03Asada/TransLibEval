import unittest
from function_levenshtein_distance import StringProcessor

class TestLevenshteinDistance(unittest.TestCase):

    def setUp(self):
        self.processor = StringProcessor()

    def test_equal_strings(self):
        result = self.processor.calculate_levenshtein_distance("test", "test")
        self.assertEqual(result, 0)

    def test_insertions_and_deletions(self):
        result = self.processor.calculate_levenshtein_distance("hello", "helo")
        self.assertEqual(result, 1) 

        result = self.processor.calculate_levenshtein_distance("helo", "hello")
        self.assertEqual(result, 1) 

    def test_single_substitution(self):
        result = self.processor.calculate_levenshtein_distance("flaw", "lawn")
        self.assertEqual(result, 2)

    def test_multiple_operations(self):
        result = self.processor.calculate_levenshtein_distance("kitten", "sitting")
        self.assertEqual(result, 3)

    def test_non_string_input(self):
        with self.assertRaises(TypeError):
            self.processor.calculate_levenshtein_distance(123, "hello")

        with self.assertRaises(TypeError):
            self.processor.calculate_levenshtein_distance("hello", None)

if __name__ == "__main__":
    unittest.main()
