import unittest
from function_lightgbm_eval import EvalFunction

class TestEvalFunction(unittest.TestCase):
    def setUp(self):
        self.eval_function = EvalFunction()
        
        self.test_data = "0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5"

    def test_model_training_with_eval_metric(self):
        try:
            result = self.eval_function.evaluate(self.test_data)
        except Exception as e:
            self.fail(f"Model training or prediction failed with exception: {e}")
    
    def test_eval_metric_logloss(self):
        modified_test_data = "0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5"
        result = self.eval_function.evaluate(modified_test_data)
        self.assertIsInstance(result, float)
        self.assertGreater(result, 0.0)

    def test_eval_metric_auc(self):
        modified_test_data = "0.3,0.5,0.1,0.2,0.4;0.4,0.3,0.2,0.1,0.5;0.6,0.1,0.5,0.4,0.3;0.1,0.2,0.3,0.4,0.5;0.2,0.4,0.1,0.5,0.3;0.3,0.5,0.2,0.1,0.4"
        result = self.eval_function.evaluate(modified_test_data)
        self.assertIsInstance(result, float)
        self.assertGreater(result, 0.4)

    def test_eval_with_high_precision(self):
        high_precision_data = "0.998,0.999,0.997,0.996,0.995;0.994,0.995,0.996,0.997,0.998;0.995,0.996,0.997,0.998,0.999"
        result = self.eval_function.evaluate(high_precision_data)
        self.assertIsInstance(result, float)
        self.assertGreater(result, 0.5)  

    def test_eval_with_invalid_data(self):
        invalid_test_data = "0.1,0.2,0.3,0.4;0.1,0.2,0.3"
        with self.assertRaises(ValueError):
            self.eval_function.evaluate(invalid_test_data)

if __name__ == '__main__':
    unittest.main()
