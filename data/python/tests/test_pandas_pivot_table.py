import unittest
from function_pandas_pivot_table import CreatePivotTable

class TestCreatePivotTable(unittest.TestCase):

    def test_success(self):
        calc_pivot = CreatePivotTable()
        result = calc_pivot.create_pivot_table()
        self.assertTrue(result.startswith("Date:"))

    def test_pivot_table_structure(self):
        calc_pivot = CreatePivotTable()
        result = calc_pivot.create_pivot_table()
        self.assertIn("Date:", result)

    def test_column_names(self):
        calc_pivot = CreatePivotTable()
        result = calc_pivot.create_pivot_table()
        # 检查透视表中的列名是否正确
        self.assertIn("Category A:", result)
        self.assertIn("Category B:", result)

    def test_result_format(self):
        calc_pivot = CreatePivotTable()
        result = calc_pivot.create_pivot_table()
        self.assertIsInstance(result, str)

    def test_missing_value_check(self):
        calc_pivot = CreatePivotTable()
        result = calc_pivot.create_pivot_table(date1="2023-01-01", date2="2023-01-02", category1="A", category2="B", value1=5, value2=None)
        self.assertIn("Category B: 0", result)

if __name__ == '__main__':
    unittest.main()
