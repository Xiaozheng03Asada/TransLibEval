import unittest
from function_functools_partial import PartialFunctionExample

class TestPartialFunctionExample(unittest.TestCase):

    def test_apply_partial_function(self):
        calc = PartialFunctionExample()
        result = calc.apply_partial_function(5, 10)
        self.assertEqual(result, 15)

    def test_apply_partial_function_with_negative(self):
        calc = PartialFunctionExample()
        result = calc.apply_partial_function(-3, 7)
        self.assertEqual(result, 4)

    def test_apply_partial_function_zero(self):
        calc = PartialFunctionExample()
        result = calc.apply_partial_function(0, 5)
        self.assertEqual(result, 5)

    def test_non_numeric_input(self):
        calc = PartialFunctionExample()
        with self.assertRaises(TypeError):
            calc.apply_partial_function("five", 10)

    def test_edge_case(self):
        calc = PartialFunctionExample()
        result = calc.apply_partial_function(0, 0)
        self.assertEqual(result, 0)

if __name__ == '__main__':
    unittest.main()
