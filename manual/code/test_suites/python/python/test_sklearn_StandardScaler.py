import unittest
import numpy as np
from function_sklearn_StandardScaler import StandardScalerFunction

class TestStandardScalerTestCase(unittest.TestCase):

    def test_shape_consistency(self):
        data = "1.0,-1.0,2.0;2.0,0.0,0.0;0.0,1.0,-1.0;3.0,2.0,1.0"
        result = StandardScalerFunction.quick_sort_from_string(data)
        self.assertTrue(isinstance(result, str))

    def test_mean_value_near_zero(self):
        data = "1.0,-1.0,2.0;2.0,0.0,0.0;0.0,1.0,-1.0;3.0,2.0,1.0"
        result = StandardScalerFunction.quick_sort_from_string(data)
        result_array = np.array([list(map(float, item.split(','))) for item in result.split(';')])
        self.assertTrue(np.allclose(np.mean(result_array, axis=0), 0, atol=0.1))

    def test_std_value_near_one(self):
        data = "1.0,-1.0,2.0;2.0,0.0,0.0;0.0,1.0,-1.0;3.0,2.0,1.0"
        result = StandardScalerFunction.quick_sort_from_string(data)
        result_array = np.array([list(map(float, item.split(','))) for item in result.split(';')])
        self.assertTrue(np.allclose(np.std(result_array, axis=0), 1, atol=0.1))

    def test_invalid_data_type(self):
        data = "a,2;3,b;5,6"
        with self.assertRaises(ValueError):
            StandardScalerFunction.quick_sort_from_string(data)

    def test_2d_array_data(self):
        data = "[[[1, 2], [3, 4]], [[5, 6], [7, 8]]]"
        with self.assertRaises(ValueError):
            StandardScalerFunction.quick_sort_from_string(data)

if __name__ == '__main__':
    unittest.main()
