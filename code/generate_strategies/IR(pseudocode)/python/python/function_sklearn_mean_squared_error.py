from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error

class MeanSquaredErrorCalculator:
    def calculate(self, X: str, y: str) -> str:
        X = [float(i) for i in X.split(',')]
        y = [float(i) for i in y.split(',')]
        
        if len(X) != len(y):
            raise ValueError("The number of samples in X and y must be the same.")
        
        model = LinearRegression()
        model.fit([[float(i)] for i in X], y)
        y_pred = model.predict([[float(i)] for i in X])
        
        return str(mean_squared_error(y, y_pred))
