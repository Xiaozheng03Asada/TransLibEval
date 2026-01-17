from catboost import CatBoostClassifier

class CatBoostProbabilityPredictor:
    def predict_probability(self, x1: float, x2: float, x3: float, x4: float, x5: float, 
                            y1: int, y2: int, y3: int, y4: int, y5: int, value: float) -> float:
   
        if not all(isinstance(x, (int, float)) for x in [x1, x2, x3, x4, x5, value]):
            raise ValueError("Feature values and input must be integers or floats.")
        if not all(isinstance(y, int) for y in [y1, y2, y3, y4, y5]):
            raise ValueError("Labels must be integers.")

        train_data = [[x1], [x2], [x3], [x4], [x5]]
        train_labels = [y1, y2, y3, y4, y5]

        model = CatBoostClassifier(iterations=10, depth=3, learning_rate=0.1, verbose=0)
      
        model.fit(train_data, train_labels)
        
        probabilities = model.predict_proba([[value]])
        return float(probabilities[0][1])
