import unittest
from function_lightgbm_train import LightGBMTrainer

class TestTrainFunction(unittest.TestCase):

    def test_train_params(self):
        trainer = LightGBMTrainer()
        boosting_type = 'gbdt'
        objective = 'binary'
        metric = 'binary_logloss'
        num_leaves = 31
        learning_rate = 0.05
        feature_fraction = 0.9
        bagging_fraction = 0.8
        bagging_freq = 5
        verbose = 0
        num_rounds = 1
        data_size = 100
        feature_size = 5
        model = trainer.train(boosting_type, objective, metric, num_leaves, learning_rate,
                              feature_fraction, bagging_fraction, bagging_freq, verbose, num_rounds, data_size, feature_size)
        self.assertTrue(0 <= model <= 1)  
    
    def test_model_training(self):
        trainer = LightGBMTrainer()
        boosting_type = 'gbdt'
        objective = 'binary'
        metric = 'binary_logloss'
        num_leaves = 31
        learning_rate = 0.05
        feature_fraction = 0.9
        bagging_fraction = 0.8
        bagging_freq = 5
        verbose = 0
        num_rounds = 1
        data_size = 100
        feature_size = 5
        model = trainer.train(boosting_type, objective, metric, num_leaves, learning_rate,
                              feature_fraction, bagging_fraction, bagging_freq, verbose, num_rounds, data_size, feature_size)
        self.assertTrue(0 <= model <= 1)  

    def test_prediction_length(self):
        trainer = LightGBMTrainer()
        boosting_type = 'gbdt'
        objective = 'binary'
        metric = 'binary_logloss'
        num_leaves = 31
        learning_rate = 0.05
        feature_fraction = 0.9
        bagging_fraction = 0.8
        bagging_freq = 5
        verbose = 0
        num_rounds = 1
        data_size = 100
        feature_size = 5
        predictions = trainer.train(boosting_type, objective, metric, num_leaves, learning_rate,
                                    feature_fraction, bagging_fraction, bagging_freq, verbose, num_rounds, data_size, feature_size)
        self.assertTrue(0 <= predictions <= 1) 

    def test_missing_param_error(self):
        trainer = LightGBMTrainer()
        boosting_type = 'gbdt'
        objective = ''  
        metric = 'binary_logloss'
        num_leaves = 31
        learning_rate = 0.05
        feature_fraction = 0.9
        bagging_fraction = 0.8
        bagging_freq = 5
        verbose = 0
        num_rounds = 1
        data_size = 100
        feature_size = 5
        with self.assertRaises(KeyError):
            trainer.train(boosting_type, objective, metric, num_leaves, learning_rate,
                          feature_fraction, bagging_fraction, bagging_freq, verbose, num_rounds, data_size, feature_size)

    def test_invalid_round_type_error(self):
        trainer = LightGBMTrainer()
        boosting_type = 'gbdt'
        objective = 'binary'
        metric = 'binary_logloss'
        num_leaves = 31
        learning_rate = 0.05
        feature_fraction = 0.9
        bagging_fraction = 0.8
        bagging_freq = 5
        verbose = 0
        num_rounds = "invalid" 
        data_size = 100
        feature_size = 5
        with self.assertRaises(TypeError):
            trainer.train(boosting_type, objective, metric, num_leaves, learning_rate,
                          feature_fraction, bagging_fraction, bagging_freq, verbose, num_rounds, data_size, feature_size)

if __name__ == '__main__':
    unittest.main()
