import unittest
from function_pandas_fill_missing import FillMissingValues

class TestFillMissingValues(unittest.TestCase):

    def test_success(self):
        fill_missing = FillMissingValues()
        result = fill_missing.fill_missing_values(1, None)
        self.assertTrue(result.startswith("A: 1"))

    def test_filled_values(self):
        fill_missing = FillMissingValues()
        result = fill_missing.fill_missing_values(None, None)
        self.assertEqual(result, "A: nan, B: nan")

    def test_result_format(self):
        fill_missing = FillMissingValues()
        result = fill_missing.fill_missing_values(5, 20)
        self.assertIsInstance(result, str)

    def test_column_names(self):
        fill_missing = FillMissingValues()
        result = fill_missing.fill_missing_values(7, 14)
        self.assertIn("A", result)
        self.assertIn("B", result)

    def test_partial_filled_values(self):
        fill_missing = FillMissingValues()
        result = fill_missing.fill_missing_values(None, 50)
        self.assertEqual(result, "A: nan, B: 50")

if __name__ == '__main__':
    unittest.main()
