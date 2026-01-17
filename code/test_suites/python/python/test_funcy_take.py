import unittest
from function_funcy_take import IterableProcessor

class TestFuncyTake(unittest.TestCase):

    def setUp(self):
        self.processor = IterableProcessor()

    def test_get_first_n_elements(self):
        result = self.processor.get_first_n_elements("[1, 2, 3, 4, 5]", 3)
        self.assertEqual(result, "[1, 2, 3]")

    def test_more_elements_than_list(self):
        result = self.processor.get_first_n_elements("[1, 2]", 5)
        self.assertEqual(result, "[1, 2]")

    def test_take_large_n(self):
        result = self.processor.get_first_n_elements("[1, 2, 3]", 100)
        self.assertEqual(result, "[1, 2, 3]")

    def test_take_with_strings(self):
        result = self.processor.get_first_n_elements("['apple', 'banana', 'cherry', 'date']", 2)
        self.assertEqual(result, "['apple', 'banana']")

    def test_invalid_input(self):
        result = self.processor.get_first_n_elements("12345", 3)
        self.assertEqual(result, "Error: invalid input")
 
if __name__ == '__main__':
    unittest.main()
