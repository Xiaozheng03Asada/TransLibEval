from mlxtend.feature_selection import SequentialFeatureSelector
from sklearn.datasets import load_iris
from sklearn.linear_model import LogisticRegression
import pandas as pd

class FeatureSelector:
    def sequential_feature_selection(self, features: str, target: str, k_features: int) -> str:
        data = load_iris()
        X = pd.DataFrame(data.data, columns=data.feature_names)
        y = pd.Series(data.target)
        
        if k_features < 1 or k_features > X.shape[1]:
            return "[]"
        
        model = LogisticRegression(max_iter=200)
        sfs = SequentialFeatureSelector(model, 
                                        k_features=k_features, 
                                        forward=True, 
                                        floating=False, 
                                        scoring='accuracy', 
                                        cv=5)
        
        sfs = sfs.fit(X, y)
        
        selected_features = sfs.k_feature_idx_
        
        selected_feature_names = [X.columns[i] for i in selected_features]
        
        return str(sorted(selected_feature_names))
