import statsmodels.api as sm
import pandas as pd

class OLSRegression:
    def perform_ols_regression(self, data_str: str, dependent_var: str, independent_vars_str: str) -> str:
        data = eval(data_str)  
        independent_vars = independent_vars_str.split(',')  
        if not isinstance(data, dict):
            raise TypeError("Data must be a dictionary.")
        if not all(isinstance(i, (int, float)) for i in data[dependent_var]):
            raise ValueError("Dependent variable contains non-numeric data.")
        
        if len(data[dependent_var]) < 2: 
            raise ValueError("Insufficient data for regression.")

        X = sm.add_constant(pd.DataFrame(data)[independent_vars])
        y = pd.Series(data[dependent_var])

        model = sm.OLS(y, X).fit()
        
        return f"R-squared: {model.rsquared}, Coefficients: {model.params.to_dict()}"
