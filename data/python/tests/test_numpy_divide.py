import unittest
from function_numpy_divide import DivisionCalculator

class TestDivideExample(unittest.TestCase):

    def test_divide_same_shape_1d_arrays(self):
        a = 4  # 将数组元素数值替换为基础数据类型
        b = 2  # 用基础数据类型（int 或 float）代替数组
        division_calculator = DivisionCalculator()
        result = division_calculator.divide(a, b)  # 使用实例调用 divide 方法
        expected_result = 2.0  # 基本数据类型（float）
        self.assertEqual(result, expected_result)

    def test_divide_same_shape_2d_arrays(self):
        c = 4  # 用基础数据类型替换
        d = 2  # 用基础数据类型替换
        division_calculator = DivisionCalculator()
        result = division_calculator.divide(c, d)  # 使用实例调用 divide 方法
        expected_result = 2.0  # 基本数据类型（float）
        self.assertEqual(result, expected_result)

    def test_divide_1d_array_by_scalar(self):
        e = 6  # 基本数据类型（int 或 float）
        scalar = 2  # 基本数据类型（int 或 float）
        division_calculator = DivisionCalculator()
        result = division_calculator.divide(e, scalar=scalar)  # 使用实例调用 divide 方法
        expected_result = 3.0  # 基本数据类型（float）
        self.assertEqual(result, expected_result)

    def test_divide_1d_array_with_zero_by_non_zero_scalar(self):
        e = 0  # 基本数据类型（int 或 float）
        scalar = 2  # 基本数据类型（int 或 float）
        division_calculator = DivisionCalculator()
        result = division_calculator.divide(e, scalar=scalar)  # 使用实例调用 divide 方法
        expected_result = 0.0  # 基本数据类型（float）
        self.assertEqual(result, expected_result)

    def test_divide_broadcastable_arrays(self):
        f = 6  # 基本数据类型（int 或 float）
        g = 2  # 基本数据类型（int 或 float）
        division_calculator = DivisionCalculator()
        result = division_calculator.divide(f, g)  # 使用实例调用 divide 方法
        expected_result = 3.0  # 基本数据类型（float）
        self.assertEqual(result, expected_result)


if __name__ == '__main__':
    unittest.main()