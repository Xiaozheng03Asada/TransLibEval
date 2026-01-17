import unittest
from function_pyphonetics_compare import SoundexProcessor
class TestSoundexProcessor(unittest.TestCase):

    def setUp(self):
        self.processor = SoundexProcessor()

    def test_identical_strings(self):
        result = self.processor.compare_strings("hello", "hello")
        self.assertEqual(result, True)

    def test_similar_sounding_words(self):
        result = self.processor.compare_strings("hello", "halo")
        self.assertEqual(result, True)

    def test_different_sounding_words(self):
        result = self.processor.compare_strings("hello", "world")
        self.assertEqual(result, False)

    def test_empty_strings(self):
        result = self.processor.compare_strings("", "")
        self.assertEqual(result, "The given string is empty.")

    def test_empty_and_non_empty(self):
        result = self.processor.compare_strings("hello", "")
        self.assertEqual(result, "The given string is empty.")


if __name__ == "__main__":
    unittest.main()