import unittest
from function_algorithms_sort_quick_sort import QuickSortFunction

class TestQuickSortFunction(unittest.TestCase):
    def setUp(self):
        self.function = QuickSortFunction()

    def test_normal_list(self):
        data_str = "4,2,9,1,7"
        expected = "1,2,4,7,9"
        self.assertEqual(self.function.quick_sort_from_string(data_str), expected)

    def test_already_sorted(self):
        data_str = "1,2,3,4,5"
        expected = "1,2,3,4,5"
        self.assertEqual(self.function.quick_sort_from_string(data_str), expected)

    def test_reverse_sorted(self):
        data_str = "5,4,3,2,1"
        expected = "1,2,3,4,5"
        self.assertEqual(self.function.quick_sort_from_string(data_str), expected)

    def test_empty_list(self):
        data_str = ""
        expected = ""
        self.assertEqual(self.function.quick_sort_from_string(data_str), expected)

    def test_single_element(self):
        data_str = "42"
        expected = "42"
        self.assertEqual(self.function.quick_sort_from_string(data_str), expected)

if __name__ == "__main__":
    unittest.main()
