import unittest
from function_autopep8_fix_file import AutoPep8Formatter

class TestAutoPep8(unittest.TestCase):

    def setUp(self):
        self.formatter = AutoPep8Formatter()

    def test_format_python_code_basic(self):
        code = "def bad_function(x,y):print(x+y)\n"
        expected = "def bad_function(x, y): print(x+y)\n"
        self.assertEqual(self.formatter.format_code(code), expected)

    def test_format_python_code_with_empty_lines(self):
        code = "\ndef bad_function(x,y):print(x+y)\n\ndef another_function(a,b):print(a+b)\n"
        expected = "\ndef bad_function(x, y): print(x+y)\n\n\ndef another_function(a, b): print(a+b)\n"
        self.assertEqual(self.formatter.format_code(code), expected)

    def test_format_python_code_mixed_indentation(self):
        code = "\ndef bad_function(x, y):\n\tprint(x+y)\n"
        expected = "\ndef bad_function(x, y):\n    print(x+y)\n"
        self.assertEqual(self.formatter.format_code(code), expected)

    def test_format_python_code_no_function(self):
        code = "\nx = 10\ny = 20\nz = x + y\nprint(z)\n"
        expected = "\nx = 10\ny = 20\nz = x + y\nprint(z)\n"
        self.assertEqual(self.formatter.format_code(code), expected)

    def test_format_python_code_single_line(self):
        code = "x=1;y=2;z=x+y;print(z)\n"
        expected = "x = 1\ny = 2\nz = x+y\nprint(z)\n"
        self.assertEqual(self.formatter.format_code(code), expected)

if __name__ == "__main__":
    unittest.main()
