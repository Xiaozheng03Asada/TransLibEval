import numpy as np


class DivisionCalculator:

    def divide(self, arr1, arr2=None, scalar=None):
        try:
            if arr2 is not None:  # 两个数组相除
                # 确保输入为基本数据类型（int、float）
                if not isinstance(arr1, (int, float)) or not isinstance(arr2, (int, float)):
                    raise ValueError("Both arr1 and arr2 must be basic data types (int or float).")

                # 进行除法运算
                result = arr1 / arr2
                return result  # 返回结果为基本数据类型（float）

            elif scalar is not None:  # 数组与标量相除
                if not isinstance(arr1, (int, float)):
                    raise ValueError("arr1 must be a basic data type (int or float) when dividing by scalar.")

                # 进行除法运算
                result = arr1 / scalar
                return result  # 返回结果为基本数据类型（float）

            else:
                raise ValueError("Either arr2 or scalar must be provided.")

        except Exception as e:
            raise ValueError(f"Error in division: {e}")