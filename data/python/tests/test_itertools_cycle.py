import unittest
from function_itertools_cycle import CycleProcessor

class TestCycleExample(unittest.TestCase):

    def setUp(self):
        self.processor = CycleProcessor()

    def test_cycle_example_empty_sequence(self):
        result = self.processor.test_cycle('', 5)
        self.assertEqual(result, '')

    def test_cycle_example_single_character(self):
        result = self.processor.test_cycle('A', 5)
        self.assertEqual(result, 'AAAAA')

    def test_cycle_example_short_sequence(self):
        result = self.processor.test_cycle('AB', 5)
        self.assertEqual(result, 'ABABA')

    def test_cycle_example_long_sequence(self):
        result = self.processor.test_cycle('ABCDEFG', 10)
        self.assertEqual(result, 'ABCDEFGABC')

    def test_cycle_example_numeric_sequence(self):
        result = self.processor.test_cycle('123', 7)
        self.assertEqual(result, '1231231')

if __name__ == '__main__':
    unittest.main()
