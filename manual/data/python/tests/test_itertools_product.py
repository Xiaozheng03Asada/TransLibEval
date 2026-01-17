import unittest
from function_itertools_product import CartesianProductProcessor


class TestCartesianProduct(unittest.TestCase):

    def setUp(self):
        self.processor = CartesianProductProcessor()

    def test_cartesian_product_one_element_in_three_lists(self):
        input_string = "1;2,3;4,5"
        expected_result = "124;125;134;135"
        result = self.processor.test_product(input_string)
        self.assertEqual(sorted(result.split(';')), sorted(expected_result.split(';')))

    def test_cartesian_product_negative_numbers(self):
        input_string = "-1,0;-2,2"
        expected_result = "-1-2;-12;0-2;02"
        result = self.processor.test_product(input_string)
        self.assertEqual(sorted(result.split(';')), sorted(expected_result.split(';')))

    def test_cartesian_product_none_input(self):
        with self.assertRaises(ValueError):
            self.processor.test_product(None)

    def test_cartesian_product_non_iterable_input(self):
        with self.assertRaises(ValueError):
            self.processor.test_product(123)

    def test_cartesian_product_elements_not_iterable(self):
        with self.assertRaises(ValueError):
            self.processor.test_product("1,2;;4,5")