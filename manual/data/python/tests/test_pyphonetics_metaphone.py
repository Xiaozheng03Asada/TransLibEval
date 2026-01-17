import unittest
from function_pyphonetics_metaphone import PhoneticProcessor
class TestPhoneticProcessor(unittest.TestCase):

    def setUp(self):
        self.processor = PhoneticProcessor()

    def test_basic_string(self):
        result = self.processor.generate_phonetics("discrimination")
        self.assertEqual(result, "TSKRMNXN")

    def test_empty_string(self):
        result = self.processor.generate_phonetics("")
        self.assertEqual(result, 'The given string is empty.')

    def test_single_word(self):
        result = self.processor.generate_phonetics("hello")
        self.assertEqual(result, "HL")

    def test_case_insensitivity(self):
        result = self.processor.generate_phonetics("Hello")
        self.assertEqual(result, "HL")

    def test_long_word(self):
        result = self.processor.generate_phonetics("internationalization")
        self.assertEqual(result, "INTRNXNLSXN")

if __name__ == "__main__":
    unittest.main()
