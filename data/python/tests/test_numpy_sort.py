import unittest
from function_numpy_sort import SortCalculator

class TestSortCalculator(unittest.TestCase):

    def test_valid_input(self):
        calc_sort = SortCalculator()
        result = calc_sort.sort(10, 5, 15)  # 输入 10, 5, 15
        self.assertIn("Sorted Values: 5", result)
        self.assertIn("10", result)
        self.assertIn("15", result)

    def test_empty_input(self):
        calc_sort = SortCalculator()
        result = calc_sort.sort()
        self.assertIn("Sorted Values: 5", result)
        self.assertIn("10", result)
        self.assertIn("15", result)

    def test_single_value_input(self):
        calc_sort = SortCalculator()
        result = calc_sort.sort(100, 100, 100)
        self.assertIn("Sorted Values: 100", result)

    def test_large_numbers(self):
        calc_sort = SortCalculator()
        result = calc_sort.sort(1e10, 1e12, 1e11)
        self.assertIn("Sorted Values: 10000000000.0", result)
        self.assertIn("1000000000000.0", result)
        self.assertIn("100000000000.0", result)

    def test_mixed_sign_values(self):
        calc_sort = SortCalculator()
        result = calc_sort.sort(-10, 0, 5)
        self.assertIn("Sorted Values: -10", result)
        self.assertIn("0", result)
        self.assertIn("5", result)

if __name__ == '__main__':
    unittest.main()
