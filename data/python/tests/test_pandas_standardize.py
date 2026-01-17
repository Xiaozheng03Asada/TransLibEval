import unittest
from function_pandas_standardize import StandardizeData

class TestStandardizeData(unittest.TestCase):

    def test_success(self):
        calc_standardize = StandardizeData()
        result = calc_standardize.standardize(1, 2)
        self.assertTrue(result.startswith("A:"))

    def test_result_format(self):
        calc_standardize = StandardizeData()
        result = calc_standardize.standardize(1, 2)
        self.assertIsInstance(result, str)

    def test_empty_data(self):
        calc_standardize = StandardizeData()
        result = calc_standardize.standardize(None, None)
        self.assertTrue("A: None" in result)
        self.assertTrue("B: None" in result)

    def test_column_consistency(self):
        calc_standardize = StandardizeData()
        result = calc_standardize.standardize(1, 2)
        self.assertIn("A:", result)
        self.assertIn("B:", result)

    def test_same_value_input(self):
        calc_standardize = StandardizeData()
        result = calc_standardize.standardize(5, 5)  # Same value inputs, should result in standardization of 0
        self.assertIn("A: 0", result)  # Expected result should be 0
        self.assertIn("B: 0", result)  # Expected result should be 0

if __name__ == '__main__':
    unittest.main()
