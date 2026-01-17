import unittest
from function_joblib_delayed import DelayedExample

class TestDelayedExample(unittest.TestCase):

    def test_apply_delayed_function(self):
        calc = DelayedExample()
        result = calc.apply_delayed_function(5, 10)
        self.assertEqual(result, "15")

    def test_apply_delayed_function_negative(self):
        calc = DelayedExample()
        result = calc.apply_delayed_function(-3, 7)
        self.assertEqual(result, "4")

    def test_apply_delayed_function_zero(self):
        calc = DelayedExample()
        result = calc.apply_delayed_function(0, 5)
        self.assertEqual(result, "5")

    def test_non_numeric_input(self):
        calc = DelayedExample()
        with self.assertRaises(TypeError):
            calc.apply_delayed_function("five", 10)

    def test_edge_case(self):
        calc = DelayedExample()
        result = calc.apply_delayed_function(0, 0)
        self.assertEqual(result, "0")

if __name__ == '__main__':
    unittest.main()
