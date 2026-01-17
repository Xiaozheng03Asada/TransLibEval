import unittest
from function_pyphonetics_phonetics import SoundexProcessor

class TestSoundexProcessor(unittest.TestCase):

    def setUp(self):
        self.processor = SoundexProcessor()

    def test_basic_string(self):
        result = self.processor.generate_soundex("hello")
        self.assertEqual(result, "H400")

    def test_same_sound(self):
        result = self.processor.generate_soundex("halo")
        self.assertEqual(result, "H400")

    def test_different_sound(self):
        result = self.processor.generate_soundex("world")
        self.assertEqual(result, "W643")

    def test_empty_string(self):
        result = self.processor.generate_soundex("")
        self.assertEqual(result, "The given string is empty.")

    def test_single_character(self):
        result = self.processor.generate_soundex("a")
        self.assertEqual(result, "A000")


if __name__ == "__main__":
    unittest.main()