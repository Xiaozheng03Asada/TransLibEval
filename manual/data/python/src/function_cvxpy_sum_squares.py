import cvxpy as cp

class CVXPYSumOfSquaresFunction:
    def solve_sum_of_squares(self, vector_values_str: str) -> str:
        vector_values = list(map(float, vector_values_str.split(',')))
        vector = cp.Variable(len(vector_values))
        objective = cp.Minimize(cp.sum_squares(vector))
        constraints = [vector == vector_values]
        problem = cp.Problem(objective, constraints)
        problem.solve()
        return str(problem.value)
