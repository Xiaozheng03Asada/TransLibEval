import unittest
from function_itertools_repeat import RepeatExample

class TestRepeatExample(unittest.TestCase):

    def test_repeat_integer(self):
        calc = RepeatExample()
        result = calc.repeat_element(5, 3)
        self.assertEqual(result, "5, 5, 5")

    def test_repeat_string(self):
        calc = RepeatExample()
        result = calc.repeat_element("apple", 2)
        self.assertEqual(result, "apple, apple")

    def test_repeat_zero_times(self):
        calc = RepeatExample()
        result = calc.repeat_element(10, 0)
        self.assertEqual(result, "")

    def test_non_numeric(self):
        calc = RepeatExample()
        result = calc.repeat_element("banana", 4)
        self.assertIsInstance(result, str)

    def test_invalid_input(self):
        calc = RepeatExample()
        with self.assertRaises(TypeError):
            calc.repeat_element("apple", "two")

if __name__ == '__main__':
    unittest.main()
