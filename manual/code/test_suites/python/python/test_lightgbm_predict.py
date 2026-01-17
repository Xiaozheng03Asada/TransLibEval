import unittest
from function_lightgbm_predict import LightGBMPredictor

class TestLightGBMPredictor(unittest.TestCase):
    def setUp(self):
        self.predictor = LightGBMPredictor()

    def test_predict_multiple_samples(self):
        input_str = "0.1,0.2,0.3,0.4,0.5;0.6,0.7,0.8,0.9,1.0"
        result = self.predictor.quick_sort_from_string(input_str)
        result_values = list(map(float, result.split(',')))
        self.assertEqual(len(result_values), 2)

    def test_predict_result_type(self):
        input_str = "0.1,0.2,0.3,0.4,0.5"
        result = self.predictor.quick_sort_from_string(input_str)
        result_values = list(map(float, result.split(',')))
        self.assertTrue(isinstance(result_values[0], float))

    def test_predict_result_range(self):
        input_str = "0.1,0.2,0.3,0.4,0.5"
        result = self.predictor.quick_sort_from_string(input_str)
        result_values = list(map(float, result.split(',')))
        for r in result_values:
            self.assertGreaterEqual(r, 0)
            self.assertLessEqual(r, 1)

    def test_predict_invalid_dimension_data(self):
        input_str = "0.1,0.2,0.3,0.4;0.1,0.2,0.3"
        with self.assertRaises(ValueError):
            self.predictor.quick_sort_from_string(input_str)

    def test_predict_non_numeric_data(self):
        input_str = "a,b,c,d,e;f,g,h,i,j"
        with self.assertRaises(ValueError):
            self.predictor.quick_sort_from_string(input_str)


if __name__ == '__main__':
    unittest.main()
