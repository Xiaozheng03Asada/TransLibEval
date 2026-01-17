import lightgbm as lgb
import numpy as np

class LightGBMTrainer:
    def train(self, boosting_type: str, objective: str, metric: str, num_leaves: int, learning_rate: float,
              feature_fraction: float, bagging_fraction: float, bagging_freq: int, verbose: int, num_rounds: int, 
              data_size: int, feature_size: int) -> float:
        np.random.seed(42)
        X = np.random.rand(data_size, feature_size)
        y = np.random.randint(2, size=data_size)

        if not isinstance(boosting_type, str):
            raise TypeError("boosting_type must be a string")
        if not isinstance(objective, str):
            raise TypeError("objective must be a string")
        if not isinstance(metric, str):
            raise TypeError("metric must be a string")

        if not isinstance(num_leaves, int):
            raise TypeError("num_leaves must be an integer")
        if not isinstance(learning_rate, float):
            raise TypeError("learning_rate must be a float")
        if not isinstance(feature_fraction, float):
            raise TypeError("feature_fraction must be a float")
        if not isinstance(bagging_fraction, float):
            raise TypeError("bagging_fraction must be a float")
        if not isinstance(bagging_freq, int):
            raise TypeError("bagging_freq must be an integer")
        if not isinstance(verbose, int):
            raise TypeError("verbose must be an integer")
        if not isinstance(num_rounds, int):
            raise TypeError("num_rounds must be an integer")
        
        if not objective:
            raise KeyError("train_params must contain the 'objective' key")
        
        train_params = {
            'boosting_type': boosting_type,
            'objective': objective,
            'metric': metric,
            'num_leaves': num_leaves,
            'learning_rate': learning_rate,
            'feature_fraction': feature_fraction,
            'bagging_fraction': bagging_fraction,
            'bagging_freq': bagging_freq,
            'verbose': verbose
        }

        train_data = lgb.Dataset(X, label=y)
        bst = lgb.train(train_params, train_data, num_rounds)
        
        new_data = np.random.rand(10, feature_size)
        predictions = bst.predict(new_data)
        
        return float(predictions[0])
