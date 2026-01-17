import unittest
from function_collections_ordereddict import OrderedDictCalculator

class TestOrderedDictCalculator(unittest.TestCase):

    def test_single_key_value_pair(self):
        calc = OrderedDictCalculator()
        result = calc.process_key_value_pairs("apple:1")
        self.assertEqual(result, "apple:1")

    def test_multiple_key_value_pairs(self):
        calc = OrderedDictCalculator()
        result = calc.process_key_value_pairs("apple:1, banana:2, cherry:3")
        self.assertEqual(result, "apple:1, banana:2, cherry:3")

    def test_empty_input(self):
        calc = OrderedDictCalculator()
        result = calc.process_key_value_pairs("")
        self.assertEqual(result, "failed")

    def test_no_key_value_pair(self):
        calc = OrderedDictCalculator()
        result = calc.process_key_value_pairs("apple:1, , cherry:3")
        self.assertEqual(result, "apple:1, cherry:3")

    def test_non_numeric(self):
        calc = OrderedDictCalculator()
        result = calc.process_key_value_pairs("apple:one, banana:two")
        self.assertIsInstance(result, str)

if __name__ == '__main__':
    unittest.main()
