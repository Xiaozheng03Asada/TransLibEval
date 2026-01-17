import unittest
from function_sortedcontainers_SortedList_remove import HanSeok

class TestSortedListRemoveMethod(unittest.TestCase):

    def test_remove_element(self):
        sorted_list = HanSeok().remove_element_from_list(3)
        self.assertEqual(sorted_list, "1,5,8")

    def test_remove_element_not_found(self):
        result = HanSeok().remove_element_from_list(10)
        self.assertEqual(result, "Value not found")

    def test_remove_duplicate_element(self):
        sorted_list = HanSeok().remove_element_from_list(3, "1,3,3,5")
        self.assertEqual(sorted_list, "1,3,5")

    def test_remove_all_elements(self):
        sorted_list = "1,3,5,8"
        for value in sorted_list.split(","):
            sorted_list = HanSeok().remove_element_from_list(int(value), sorted_list)
        self.assertEqual(sorted_list, "")

    def test_empty_list(self):
        result = HanSeok().remove_element_from_list(1, "1")
        self.assertEqual(result, "")

if __name__ == "__main__":
    unittest.main()
