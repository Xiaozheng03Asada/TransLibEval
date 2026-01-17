import unittest
from function_autopep8_format_code_with_custom_style import CodeFormatter

class TestAutoPep8CustomStyle(unittest.TestCase):

    def test_format_code_basic(self):
        formatter = CodeFormatter()
        code = "def bad_function(x,y):print(x+y)"
        expected_code = "def bad_function(x,y):print(x+y)"
        formatted_code = formatter.format_code_with_custom_style(code)
        self.assertEqual(formatted_code, expected_code)

    def test_format_code_multiple_arguments(self):
        formatter = CodeFormatter()
        code = "def multi_param_function(a,b,c,d):return a+b+c+d"
        expected_code = "def multi_param_function(a,b,c,d):return a+b+c+d"
        formatted_code = formatter.format_code_with_custom_style(code)
        self.assertEqual(formatted_code, expected_code)

    def test_format_code_multiline(self):
        formatter = CodeFormatter()
        code = "def long_function():\n    a=1\n    b=2\n    return a+b"
        expected_code = "def long_function():\n    a=1\n    b=2\n    return a+b"
        formatted_code = formatter.format_code_with_custom_style(code)
        self.assertEqual(formatted_code, expected_code)

    def test_format_code_already_correct(self):
        formatter = CodeFormatter()
        code = "def already_correct_function(a, b): return a + b"
        expected_code = "def already_correct_function(a, b): return a + b"
        formatted_code = formatter.format_code_with_custom_style(code)
        self.assertEqual(formatted_code, expected_code)

    def test_format_code_indentation(self):
        formatter = CodeFormatter()
        code = "def function():\na=1\n    b=2\n    return a+b"
        expected_code = "def function():\na=1\n    b=2\n    return a+b"
        formatted_code = formatter.format_code_with_custom_style(code)
        self.assertEqual(formatted_code, expected_code)

if __name__ == "__main__":
    unittest.main()
