import unittest
from function_boltons_strutils_slugify import TextSlugifier

class TestSlugify(unittest.TestCase):

    def setUp(self):
        self.slugifier = TextSlugifier()

    def test_slugify_basic(self):
        text = "Hello World"
        result = self.slugifier.create_slug(text)
        self.assertEqual(result, "hello-world")

    def test_slugify_with_custom_delimiter(self):
        text = "Python is awesome!"
        result = self.slugifier.create_slug(text, '_')
        self.assertEqual(result, "python_is_awesome")

    def test_slugify_special_characters(self):
        text = "Clean @!this** up$!"
        result = self.slugifier.create_slug(text)
        self.assertEqual(result, "clean-this-up")

    def test_slugify_empty_string(self):
        text = ""
        result = self.slugifier.create_slug(text)
        self.assertEqual(result, "")

    def test_slugify_non_string_input(self):
        with self.assertRaises(TypeError):
            self.slugifier.create_slug(12345)

if __name__ == "__main__":
    unittest.main()
