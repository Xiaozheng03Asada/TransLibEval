import unittest
from function_itertools_permutations import PermutationsProcessor


class TestPermutations(unittest.TestCase):

    def setUp(self):
        self.processor = PermutationsProcessor()

    def test_get_permutations_content(self):
        data = "abc"
        result = self.processor.get_permutations(data)
        expected_result = 'abc, acb, bac, bca, cab, cba\nabc: 1, acb: 1, bac: 1, bca: 1, cab: 1, cba: 1'
        self.assertEqual(result, expected_result)

    def test_permutation_duplicates(self):
        data = "aab"
        result = self.processor.get_permutations(data)
        expected_result = 'aab, aab, aba, aba, baa, baa\naab: 2, aba: 2, baa: 2'
        self.assertEqual(result, expected_result)

    def test_invalid_type_error(self):
        data = [1, 2, 3]  # 非字符串输入
        with self.assertRaises(ValueError):
            self.processor.get_permutations(data)

    def test_non_iterable_error(self):
        data = 123  # 非字符串输入
        with self.assertRaises(ValueError):
            self.processor.get_permutations(data)

    def test_permutations_case_sensitivity(self):
        data = "Aa"
        result = self.processor.get_permutations(data)
        expected_result = 'Aa, aA\nAa: 1, aA: 1'  # 调整预期值为字符串格式
        print(result)
        self.assertEqual(result, expected_result)

if __name__ == '__main__':
    unittest.main()
