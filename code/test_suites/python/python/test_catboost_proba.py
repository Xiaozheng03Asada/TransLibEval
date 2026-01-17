import unittest
from function_catboost_proba import CatBoostProbabilityPredictor

class TestCatBoostProbabilityPredictor(unittest.TestCase):

    def test_basic_probability(self):
        predictor = CatBoostProbabilityPredictor()
        result = predictor.predict_probability(1.0, 2.0, 3.0, 4.0, 5.0, 0, 0, 1, 1, 1, 3.5)
        self.assertGreaterEqual(result, 0.5)

    def test_high_probability(self):
        predictor = CatBoostProbabilityPredictor()
        result = predictor.predict_probability(10.0, 20.0, 30.0, 40.0, 50.0, 0, 0, 1, 1, 1, 35.0)
        self.assertGreaterEqual(result, 0.5)  

    def test_low_probability(self):
        predictor = CatBoostProbabilityPredictor()
        result = predictor.predict_probability(10.0, 20.0, 30.0, 40.0, 50.0, 0, 0, 1, 1, 1, 5.0)
        self.assertLess(result, 0.5)

    def test_edge_case(self):
        predictor = CatBoostProbabilityPredictor()
        result = predictor.predict_probability(1.0, 2.0, 3.0, 4.0, 5.0, 0, 0, 1, 1, 1, 10.0)
        self.assertIsInstance(result, float)

    def test_invalid_inputs(self):
        predictor = CatBoostProbabilityPredictor()
        with self.assertRaises(ValueError):
            predictor.predict_probability(1.0, 2.0, "invalid", 4.0, 5.0, 0, 0, 1, 1, 1, 3.5)

if __name__ == "__main__":
    unittest.main()
