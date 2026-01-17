import unittest
from function_levenshtein_setratio import StringProcessor

class TestSetratio(unittest.TestCase):

    def setUp(self):
        self.processor = StringProcessor()

    def test_equal_strings(self):
        result = self.processor.calculate_setratio("test", "test")
        self.assertEqual(result, 1.0)

    def test_empty_strings(self):
        result = self.processor.calculate_setratio("", "")
        self.assertEqual(result, 1.0)

    def test_completely_different_strings(self):
        result = self.processor.calculate_setratio("abc", "xyz")
        self.assertEqual(result, 0.0)

    def test_partial_match(self):
        result = self.processor.calculate_setratio("kitten", "sitting")
        self.assertEqual(result, 0.6153846153846154) 

    def test_single_character_difference(self):
        result = self.processor.calculate_setratio("a", "b")
        self.assertEqual(result, 0.0)

if __name__ == "__main__":
    unittest.main()
