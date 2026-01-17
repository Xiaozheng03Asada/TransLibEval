from patsy import dmatrices
import json
import pandas as pd

class PatsyProcessor:
    def process_formula(self, data: str, formula: str) -> str:
        try:
            df = pd.read_json(data)
            y, X = dmatrices(formula, df, return_type='dataframe')
            return json.dumps({"y_shape": y.shape, "X_shape": X.shape})
        except Exception:
            return "Error: invalid input"
