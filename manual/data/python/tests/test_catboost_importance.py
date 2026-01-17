import unittest
from function_catboost_importance import CatBoostFeatureImportance

class TestCatBoostFeatureImportance(unittest.TestCase):
    def setUp(self):
        self.importance_calculator = CatBoostFeatureImportance()

    def test_prediction_values_change(self):
        self.importance_calculator.process("train", 1.0, 2.0, 3.0, 0)
        result = self.importance_calculator.process("importance", 1.0, 2.0, 3.0, importance_type="PredictionValuesChange")
        self.assertIsInstance(result, float)

    def test_loss_function_change(self):
        self.importance_calculator.process("train", 4.0, 5.0, 6.0, 1)
        result = self.importance_calculator.process("importance", 4.0, 5.0, 6.0, importance_type="LossFunctionChange")
        self.assertIsInstance(result, float)

    def test_shap_values(self):
        self.importance_calculator.process("train", 7.0, 8.0, 9.0, 0)
        result = self.importance_calculator.process("importance", 7.0, 8.0, 9.0, importance_type="ShapValues")
        self.assertIsInstance(result, float)

    def test_invalid_importance_type(self):
        self.importance_calculator.process("train", 10.0, 11.0, 12.0, 1)
        with self.assertRaises(ValueError):
            self.importance_calculator.process("importance", 10.0, 11.0, 12.0, importance_type="InvalidType")

    def test_different_inputs(self):
        self.importance_calculator.process("train", 1.5, 2.5, 3.5, 0)
        result_1 = self.importance_calculator.process("importance", 1.5, 2.5, 3.5, importance_type="PredictionValuesChange")
        self.importance_calculator.process("train", 4.5, 5.5, 6.5, 1)
        result_2 = self.importance_calculator.process("importance", 4.5, 5.5, 6.5, importance_type="PredictionValuesChange")
        self.assertNotEqual(result_1, result_2)


if __name__ == '__main__':
    unittest.main()
