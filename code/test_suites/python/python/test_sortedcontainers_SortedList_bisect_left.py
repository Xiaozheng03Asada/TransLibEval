import unittest
from function_sortedcontainers_SortedList_bisect_left import HanSeok

class TestSortedListBisectLeftMethod(unittest.TestCase):

    def test_value_in_middle(self):
        position = HanSeok().find_insert_position(4)
        self.assertEqual(position, 2)

    def test_value_at_start(self):
        position = HanSeok().find_insert_position(0)
        self.assertEqual(position, 0)

    def test_value_at_end(self):
        position = HanSeok().find_insert_position(10)
        self.assertEqual(position, 4)

    def test_existing_value(self):
        position = HanSeok().find_insert_position(3)
        self.assertEqual(position, 1)

    def test_insert_into_empty_list(self):
        position = HanSeok().find_insert_position(5, "5")
        self.assertEqual(position, 0)

if __name__ == "__main__":
    unittest.main()
