import unittest
from function_sortedcontainers_SortedDict_popitem import SortedDictHandler

class TestSortedDictPopitemMethod(unittest.TestCase):

    def setUp(self):
        self.handler = SortedDictHandler()

    def test_pop_last_item(self):
        result = self.handler.modify_sorted_dict(-1)
        self.assertEqual(result, "{1: 'one', 3: 'three'} (5, 'five')")

    def test_pop_first_item(self):
        result = self.handler.modify_sorted_dict(0)
        self.assertEqual(result, "{3: 'three', 5: 'five'} (1, 'one')")

    def test_pop_invalid_index(self):
        result = self.handler.modify_sorted_dict(5)
        self.assertEqual(result, "error: Invalid index")

    def test_pop_middle_item(self):
        result = self.handler.modify_sorted_dict(1)
        self.assertEqual(result, "{1: 'one', 5: 'five'} (3, 'three')")

    def test_pop_all_items(self):
        result = self.handler.modify_sorted_dict(0)
        self.assertEqual(result, "{3: 'three', 5: 'five'} (1, 'one')")
        
if __name__ == "__main__":
    unittest.main()
