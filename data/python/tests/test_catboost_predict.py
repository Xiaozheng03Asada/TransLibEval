import unittest
from function_catboost_predict import CatBoostPredictor

class TestCatBoostPredictor(unittest.TestCase):

    def test_basic_classification(self):
        predictor = CatBoostPredictor()
        result = predictor.predict_class(1, 2, 3, 4, 5, 0, 0, 1, 1, 1, 3)
        self.assertEqual(result, 1)

    def test_custom_threshold(self):
        predictor = CatBoostPredictor()
        result = predictor.predict_class(10, 15, 20, 25, 30, 0, 0, 1, 1, 1, 18)
        self.assertEqual(result, 1)

    def test_edge_case_low_value(self):
        predictor = CatBoostPredictor()
        result = predictor.predict_class(5, 6, 7, 8, 9, 0, 0, 1, 1, 1, 4)
        self.assertEqual(result, 0)

    def test_edge_case_high_value(self):
        predictor = CatBoostPredictor()
        result = predictor.predict_class(10, 20, 30, 40, 50, 0, 0, 1, 1, 1, 60)
        self.assertEqual(result, 1)

    def test_invalid_inputs(self):
        predictor = CatBoostPredictor()
        with self.assertRaises(ValueError):
            predictor.predict_class(1, 2, 3, "invalid", 5, 0, 1, 1, 1, 1, 4)
        with self.assertRaises(ValueError):
            predictor.predict_class(1, 2, 3, 4, 5, 0, "not_int", 1, 1, 1, 4)

if __name__ == "__main__":
    unittest.main()
