import cvxpy as cp

class CVXPYNormFunction:
    def compute_norm(self, vector: str, p:int=2) -> str:
        
        try:
            vector = [float(x) for x in vector.strip("[]").split(",")]
        except ValueError:
            raise ValueError("Invalid input format.")
        
        norm_value = cp.norm(vector, p)
        
        return str(norm_value.value)
