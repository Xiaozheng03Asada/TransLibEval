from patsy import dmatrix
import json
import pandas as pd

class PatsyProcessor:
    def generate_matrix(self, data: str, formula: str) -> str:
        try:
            df = pd.read_json(data)
            matrix = dmatrix(formula, df, return_type='dataframe')
            return json.dumps({"matrix_shape": matrix.shape})
        except Exception:
            return "Error: invalid input"
