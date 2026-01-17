import unittest
from function_funcy_flatten import ListProcessor

class TestFuncyFlatten(unittest.TestCase):

    def setUp(self):
        self.processor = ListProcessor()

    def test_flatten_simple_list(self):
        result = self.processor.process_list("[1, [2, 3], [4, 5], 6]")
        self.assertEqual(result, "[1, 2, 3, 4, 5, 6]")

    def test_flatten_empty_elements(self):
        result = self.processor.process_list("[[], (), [1, 2], [], (3, 4)]")
        self.assertEqual(result, "[1, 2, 3, 4]")

    def test_complex_nested_structure(self):
        result = self.processor.process_list("[1, (2, 3, [4, 5]), [6, (7, 8)], 9]")
        self.assertEqual(result, "[1, 2, 3, 4, 5, 6, 7, 8, 9]")

    def test_flatten_with_mixed_data_types(self):
        result = self.processor.process_list("[1, 'apple', [3.14, 'banana'], (2, 'orange'), [[True, False]]]")
        self.assertEqual(result, "[1, 'apple', 3.14, 'banana', 2, 'orange', True, False]")

    def test_flatten_non_iterable(self):
        result = self.processor.process_list("12345")
        self.assertEqual(result, "Error: input is not iterable")

if __name__ == "__main__":
    unittest.main()
