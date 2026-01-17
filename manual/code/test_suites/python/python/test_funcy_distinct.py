import unittest
from function_funcy_distinct import ListProcessor

class TestFuncyDistinct(unittest.TestCase):

    def setUp(self):
        self.processor = ListProcessor()

    def test_remove_duplicates(self):
        result = self.processor.process_list("1,2,3,1,4,2,5")
        self.assertEqual(result, "1,2,3,4,5")

    def test_all_unique_elements(self):
        result = self.processor.process_list("1,2,3,4,5")
        self.assertEqual(result, "1,2,3,4,5")

    def test_mixed_types(self):
        result = self.processor.process_list("1,apple,1,banana,apple,3.14,3.14")
        self.assertEqual(result, "1,apple,banana,3.14")

    def test_mixed_case_strings(self):
        result = self.processor.process_list("apple,Apple,banana,apple,Banana")
        self.assertEqual(result, "apple,Apple,banana,Banana")

    def test_large_list_with_duplicates(self):
        input_str = ",".join(["1"] * 1000 + ["2"] * 1000 + ["3"] * 1000)
        result = self.processor.process_list(input_str)
        self.assertEqual(result, "1,2,3")

if __name__ == '__main__':
    unittest.main()
