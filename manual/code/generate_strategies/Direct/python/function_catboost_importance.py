from catboost import CatBoostClassifier, Pool
import numpy as np

class CatBoostFeatureImportance:
    def process(self, action: str, feature1: float, feature2: float, feature3: float, label: int = None, importance_type: str = None) -> float:
        
        if not hasattr(self, 'model'):
            self.model = CatBoostClassifier(iterations=500, depth=6, learning_rate=0.05, verbose=0)
            self.train_pool = None 
        if action == "train":
            if label is None:
                raise ValueError("Label is required for training.")
            train_features = [
                (feature1, feature2, feature3),
                (feature1 + 0.1, feature2 - 0.2, feature3 + 0.3), 
                (feature1 - 0.2, feature2 + 0.1, feature3 - 0.1), 
            ]
            train_labels = [label, 1 - label, label]
            self.train_pool = Pool(train_features, train_labels)
            self.model.fit(self.train_pool)
            return 0.0  

        elif action == "importance":
            if importance_type not in {"PredictionValuesChange", "LossFunctionChange", "ShapValues"}:
                raise ValueError("Invalid importance type. Choose from 'PredictionValuesChange', 'LossFunctionChange', or 'ShapValues'.")
            
            if importance_type in {"LossFunctionChange", "ShapValues"}:
                if not self.train_pool:
                    raise ValueError("Training dataset is required for this importance type.")
                importances = self.model.get_feature_importance(self.train_pool, type=importance_type)
            else:
                importances = self.model.get_feature_importance(type=importance_type)
            
            if importance_type == "ShapValues":
                importances = np.mean(importances, axis=0)  

            return float(importances[0]) 

        else:
            raise ValueError("Invalid action. Use 'train' or 'importance'.")
