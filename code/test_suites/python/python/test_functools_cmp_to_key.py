import unittest
from function_functools_cmp_to_key import NumberDictManager

class TestNumberDictManager(unittest.TestCase):

    def setUp(self):
        self.manager = NumberDictManager()

    def test_add_operation(self):
        result = self.manager.manage_number_dict("add,1,10;add,2,20;")
        expected = ""
        self.assertEqual(result, expected)

    def test_remove_operation(self):
        result = self.manager.manage_number_dict("add,1,10;remove,1;get,1;")
        expected = "default"
        self.assertEqual(result, expected)

    def test_get_operation(self):
        result = self.manager.manage_number_dict("add,3,30;get,3;")
        expected = "30"
        self.assertEqual(result, expected)

    def test_sort_operation(self):
        result = self.manager.manage_number_dict("add,3,30;add,1,10;add,2,20;sort;")
        expected = "1:10 2:20 3:30"
        self.assertEqual(result, expected)

    def test_sum_operation(self):
        result = self.manager.manage_number_dict("add,1,10;add,2,20;sum;")
        expected = "30"
        self.assertEqual(result, expected)

if __name__ == '__main__':
    unittest.main()
