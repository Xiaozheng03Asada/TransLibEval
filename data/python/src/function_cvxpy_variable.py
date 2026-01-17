import cvxpy as cp

class CVXPYVariableFunction:
    def process_variables(self, x_value: str = None, y_value: str = None, constraint: str = None) -> str:
        if x_value is None and y_value is None and constraint is None:
           
            x = cp.Variable()
            y = cp.Variable()
            return str(x), str(y)

        if x_value is not None and y_value is not None:
            
            try:
                x = float(x_value)
                y = float(y_value)
                if x < 0 or y < 0:
                    return "Error: Variable size cannot be negative"
                return f"x: {x}, y: {y}"
            except ValueError:
                return "Error: Invalid value for x or y"

        if constraint is not None:
            if isinstance(constraint, str):
                return "Error: Invalid variable constraint"
            return "Constraint is valid"

        return "Error: Invalid input"
