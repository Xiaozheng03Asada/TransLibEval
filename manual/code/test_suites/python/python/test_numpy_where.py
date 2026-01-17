import unittest
from function_numpy_where import NumpyExample

class TestNumpyExample(unittest.TestCase):

    def test_check_number_positive(self):
        calc = NumpyExample()
        result = calc.check_number(5)
        self.assertEqual(result, "positive")

    def test_check_number_negative(self):
        calc = NumpyExample()
        result = calc.check_number(-3)
        self.assertEqual(result, "negative")

    def test_check_number_zero(self):
        calc = NumpyExample()
        result = calc.check_number(0)
        self.assertEqual(result, "zero")

    def test_check_number_large_positive(self):
        calc = NumpyExample()
        result = calc.check_number(100)
        self.assertEqual(result, "positive")

    def test_check_number_large_negative(self):
        calc = NumpyExample()
        result = calc.check_number(-100)
        self.assertEqual(result, "negative")

if __name__ == '__main__':
    unittest.main()
