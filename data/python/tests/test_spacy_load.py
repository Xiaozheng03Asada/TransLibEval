import unittest
from function_spacy_load import SpacyTextProcessor

class TestSpacyText(unittest.TestCase):

    def test_normal_text(self):
        processor = SpacyTextProcessor()
        self.normal_text = "This is a normal sentence."
        expected_result = "This is a normal sentence ."
        result = processor.spacy_text(self.normal_text)
        self.assertEqual(result, expected_result)

    def test_text_with_duplicate_words(self):
        processor = SpacyTextProcessor()
        text = "apple apple banana apple"
        result = processor.spacy_text(text)
        self.assertEqual(type(result), str)
        self.assertEqual(result.count("apple"), 3)
        self.assertEqual(result.count("banana"), 1)

    def test_memory_error(self):
        processor = SpacyTextProcessor()
        try:
            text = "a" * (1024 ** 3)  # Trying to create a huge string to simulate memory error
            result = processor.spacy_text(text)
        except Exception as e:
            self.assertEqual(str(e), "Insufficient memory error")

    def test_other_error(self):
        processor = SpacyTextProcessor()
        try:
            text = 123  # Invalid input type
            result = processor.spacy_text(text)
        except Exception as e:
            self.assertEqual(str(e)[:9], "Other errors:")

    def test_multilingual_text(self):
        processor = SpacyTextProcessor()
        text = "Bonjour, this is a test. 你好"
        result = processor.spacy_text(text)
        self.assertEqual(type(result), str)
        self.assertEqual(result, "Bonjour , this is a test . 你好")

if __name__ == '__main__':
    unittest.main()
