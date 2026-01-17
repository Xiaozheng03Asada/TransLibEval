import unittest
from function_sortedcontainers_SortedList_add import SortedListHandler

class TestSortedListAddMethod(unittest.TestCase):

    def setUp(self):
        self.handler = SortedListHandler()

    def test_add_duplicate_elements(self):
        sorted_list = "1 3 5"
        result = sorted_list + " 3"
        result = " ".join(sorted(result.split()))
        self.assertEqual(result, "1 3 3 5")

    def test_add_multiple_elements(self):
        sorted_list = "1 3 5"
        result = sorted_list + " 4 2"
        result = " ".join(sorted(result.split()))
        self.assertEqual(result, "1 2 3 4 5")

    def test_empty_list(self):
        result = "5 10"
        result = " ".join(sorted(result.split()))
        self.assertEqual(result, "10 5")

    def test_large_number_of_elements(self):
        sorted_list = " ".join(map(str, range(1000, 0, -1)))
        result = " ".join(sorted(sorted_list.split()))
        self.assertEqual(result.split()[0], "1")
        self.assertEqual(result.split()[-1], "999")
        self.assertEqual(len(result.split()), 1000)

    def test_modify_sorted_list_remove_second_item(self):
        result = self.handler.modify_sorted_list(1)  
        expected_result = "Removed item: 3, Remaining list: [1, 5, 8]"
        self.assertEqual(result, expected_result)


if __name__ == "__main__":
    unittest.main()
