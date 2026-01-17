import unittest
from function_catboost_params import CatBoostParamFetcher

class TestCatBoostParamFetcher(unittest.TestCase):
    def setUp(self):
        self.param_fetcher = CatBoostParamFetcher()

    def test_return_type(self):
        result = self.param_fetcher.get_model_params(150, 7, 0.1)
        self.assertIsInstance(result, str)

    def test_check_iterations(self):
        result = self.param_fetcher.get_model_params(200, 6, 0.1)
        self.assertIn("Iterations: 200", result)

    def test_check_depth(self):
        result = self.param_fetcher.get_model_params(100, 10, 0.05)
        self.assertIn("Depth: 10", result)

    def test_check_learning_rate(self):
        result = self.param_fetcher.get_model_params(100, 6, 0.01)
        self.assertIn("Learning Rate: 0.01", result)

    def test_multiple_conditions(self):
        result = self.param_fetcher.get_model_params(250, 8, 0.15)
        self.assertIn("Iterations: 250", result)
        self.assertIn("Depth: 8", result)
        self.assertIn("Learning Rate: 0.15", result)

if __name__ == "__main__":
    unittest.main()
