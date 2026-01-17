import unittest
from function_autopep8_fix_code import CodeFormatter

class TestAutoPep8(unittest.TestCase):

    def setUp(self):
        self.formatter = CodeFormatter()

    def test_simple_code(self):
        code = "def example_function(x,y):print(x+y)"
        formatted_code = self.formatter.format_code(code)
        expected_code = "def example_function(x, y): print(x+y)\n"
        self.assertEqual(formatted_code, expected_code)

    def test_multiple_issues_code(self):
        code = """
        def bad_function(a  , b):x=a+b
        """
        formatted_code = self.formatter.format_code(code)
        expected_code = """
def bad_function(a, b): x = a+b
"""
        self.assertEqual(formatted_code, expected_code)

    def test_already_formatted_code(self):
        code = "\ndef bad_function(a, b): x = a + b\n"
        formatted_code = self.formatter.format_code(code)
        self.assertEqual(formatted_code, code)

    def test_invalid_code(self):
        code = '{"name": "Alice", "age": 30}'
        formatted_code = self.formatter.format_code(code)
        self.assertEqual(formatted_code, '{"name": "Alice", "age": 30}\n')

    def test_syntax_error_code(self):
        code = "def example_function(x, y):\n    return x + y("
        try:
            formatted_code = self.formatter.format_code(code)
            self.assertIn("def example_function(x, y):", formatted_code)
        except Exception as e:
            self.fail(f"autopep8 failed with error: {e}")


if __name__ == "__main__":
    unittest.main()
