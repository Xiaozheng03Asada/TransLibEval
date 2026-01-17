import unittest
from function_lightgbm_feature_importance import FeatureImportance

class TestLightGBMFeatureImportance(unittest.TestCase):
    def setUp(self):
        self.feature_importance = FeatureImportance()

    def test_feature_importance_length(self):
        input_str = "100,5"
        result = self.feature_importance.quick_sort_from_string(input_str)
        importance_scores = list(map(int, result.split(',')))
        self.assertEqual(len(importance_scores), 5)

    def test_feature_importance_non_negative(self):
        input_str = "100,5"
        result = self.feature_importance.quick_sort_from_string(input_str)
        importance_scores = list(map(int, result.split(',')))
        self.assertTrue(all(score >= 0 for score in importance_scores))

    def test_feature_importance_sum(self):
        input_str = "100,5"
        result = self.feature_importance.quick_sort_from_string(input_str)
        importance_scores = list(map(int, result.split(',')))
        self.assertGreater(sum(importance_scores), 0)

    def test_feature_importance_type(self):
        input_str = "100,5"
        result = self.feature_importance.quick_sort_from_string(input_str)
        self.assertIsInstance(result, str)

    def test_feature_importance_large_number_of_features(self):
        input_str = "100,1000"
        result = self.feature_importance.quick_sort_from_string(input_str)
        importance_scores = list(map(int, result.split(',')))
        self.assertEqual(len(importance_scores), 1000)

if __name__ == '__main__':
    unittest.main()
