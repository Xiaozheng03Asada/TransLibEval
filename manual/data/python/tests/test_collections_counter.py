import unittest
from function_collections_counter import CounterCalculator

class TestCounterCalculator(unittest.TestCase):

    def test_count_single_element(self):
        calc = CounterCalculator()
        result = calc.count_elements("apple")
        self.assertEqual(result, "apple:1")

    def test_count_multiple_elements(self):
        calc = CounterCalculator()
        result = calc.count_elements("apple orange apple banana")
        self.assertEqual(result, "apple:2, orange:1, banana:1")

    def test_empty_input(self):
        calc = CounterCalculator()
        result = calc.count_elements("")
        self.assertEqual(result, "failed")

    def test_no_repeat_elements(self):
        calc = CounterCalculator()
        result = calc.count_elements("apple orange banana")
        self.assertEqual(result, "apple:1, orange:1, banana:1")

    def test_non_numeric(self):
        calc = CounterCalculator()
        result = calc.count_elements("a b c a b c")
        self.assertIsInstance(result, str)

if __name__ == '__main__':
    unittest.main()
