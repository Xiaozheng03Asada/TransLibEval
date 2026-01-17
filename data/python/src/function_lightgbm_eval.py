import lightgbm as lgb
import numpy as np

class EvalFunction:
    def evaluate(self, test_data: str) -> float:
        test_data = np.array([list(map(float, row.split(','))) for row in test_data.split(';')])

        if test_data.shape[1] != 5:
            raise ValueError("The dimension of the test data is inconsistent with that of the training data")

        data = np.random.rand(100, 5)
        labels = np.random.randint(2, size=100)
        train_data = lgb.Dataset(data, label=labels)
        model = lgb.train({'objective': 'binary'}, train_data, num_boost_round=5)

        y_pred = model.predict(test_data)

        eval_result = np.mean(y_pred)
        return eval_result
