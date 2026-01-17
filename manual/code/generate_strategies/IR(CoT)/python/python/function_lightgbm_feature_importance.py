import lightgbm as lgb
import numpy as np

class FeatureImportance:
    def quick_sort_from_string(self, input_str: str) -> str:
        num_samples, num_features = map(int, input_str.split(','))
        X = np.random.rand(num_samples, num_features)
        y = (np.sum(X, axis=1) > (num_features / 2)).astype(int)
        train_data = lgb.Dataset(X, label=y)
        params = {
            'boosting_type': 'gbdt',
            'objective': 'binary',
            'metric': 'binary_logloss',
            'num_leaves': 31,
            'learning_rate': 0.05,
            'feature_fraction': 0.9
        }
        model = lgb.train(params, train_data, num_boost_round=5)
        importance_scores = model.feature_importance().tolist()
        return ",".join(map(str, importance_scores))
