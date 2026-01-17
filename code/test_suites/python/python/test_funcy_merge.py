import unittest
from function_funcy_merge import DictMerger

class TestFuncyMerge(unittest.TestCase):

    def setUp(self):
        self.merger = DictMerger()

    def test_combine_dicts(self):
        result = self.merger.combine_dicts("{'a': 1, 'b': 2}", "{'b': 3, 'c': 4}")
        self.assertEqual(result, "{'a': 1, 'b': 3, 'c': 4}")

    def test_overriding_keys(self):
        result = self.merger.combine_dicts("{'a': 1, 'b': 2}", "{'a': 3, 'b': 4, 'c': 5}")
        self.assertEqual(result, "{'a': 3, 'b': 4, 'c': 5}")

    def test_empty_dict(self):
        result = self.merger.combine_dicts("{}", "{'a': 1, 'b': 2}")
        self.assertEqual(result, "{'a': 1, 'b': 2}")

    def test_multiple_overriding(self):
        dict1 = "{'a': 1, 'b': 2}"
        dict2 = "{'a': 3, 'b': 4}"
        dict3 = "{'a': 5, 'd': 6}"
        result = self.merger.combine_dicts(dict1, self.merger.combine_dicts(dict2, dict3))
        self.assertEqual(result, "{'a': 5, 'b': 4, 'd': 6}")

    def test_invalid_input(self):
        result = self.merger.combine_dicts("12345", "{'a': 1, 'b': 2}")
        self.assertEqual(result, "Error: input is not a dictionary")

if __name__ == "__main__":
    unittest.main()
