import cvxpy as cp
import numpy as np

class CVXPYMaxFunction:
    def compute_max_value(self, vector: str) -> str:
        
        try:
            vector = [float(x) for x in vector.strip("[]").split(",")]
        except ValueError:
            raise ValueError("Invalid input format.")
        
        if any(np.isnan(v) for v in vector):
            raise ValueError("Input contains NaN values")
        
        max_value = cp.max(vector)

        max_value_value = max_value.value

        if max_value_value is None:
            raise ValueError("Failed to compute the max value.")
        
        return str(max_value_value)
