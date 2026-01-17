import unittest
from function_algorithms_binary_search import BinarySearchFunction

class TestBinarySearchFunction(unittest.TestCase):
    def setUp(self):
        self.function = BinarySearchFunction()

    def test_target_present_in_string(self):
        data = "246810"
        target = '6'
        self.assertEqual(self.function.binary_search_index(data, target), 2)

    def test_target_absent_in_string(self):
        data = "246810"
        target = '5'
        self.assertEqual(self.function.binary_search_index(data, target), -1)

    def test_empty_string(self):
        data = ""
        target = '1'
        self.assertEqual(self.function.binary_search_index(data, target), -1)

    def test_single_character_found(self):
        data = "7"
        target = '7'
        self.assertEqual(self.function.binary_search_index(data, target), 0)

    def test_single_character_not_found(self):
        data = "7"
        target = '3'
        self.assertEqual(self.function.binary_search_index(data, target), -1)

if __name__ == "__main__":
    unittest.main()
