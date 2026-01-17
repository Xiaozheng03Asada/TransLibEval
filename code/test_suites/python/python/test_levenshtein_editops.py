import unittest
from function_levenshtein_editops import StringProcessor

class TestEditops(unittest.TestCase):

    def setUp(self):
        self.processor = StringProcessor()

    def test_equal_strings(self):
        result = self.processor.calculate_editops("test", "test")
        self.assertEqual(result, "[]")

    def test_single_substitution(self):
        result = self.processor.calculate_editops("flaw", "lawn")
        self.assertEqual(result, "[('delete', 0, 0), ('insert', 4, 3)]")

    def test_insertions_and_deletions(self):
        result = self.processor.calculate_editops("hello", "helo")
        self.assertEqual(result, "[('delete', 3, 3)]")

        result = self.processor.calculate_editops("helo", "hello")
        self.assertEqual(result, "[('insert', 3, 3)]")

    def test_multiple_operations(self):
        result = self.processor.calculate_editops("kitten", "sitting")
        self.assertEqual(result, "[('replace', 0, 0), ('replace', 4, 4), ('insert', 6, 6)]")

    def test_non_string_input(self):
        result = self.processor.calculate_editops(123, "hello")
        self.assertEqual(result, "Error: Both inputs must be strings")

        result = self.processor.calculate_editops("hello", None)
        self.assertEqual(result, "Error: Both inputs must be strings")

if __name__ == "__main__":
    unittest.main()
