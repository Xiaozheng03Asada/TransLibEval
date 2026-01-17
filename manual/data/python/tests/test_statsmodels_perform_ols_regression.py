import unittest
from function_statsmodels_perform_ols_regression import OLSRegression

class TestOLSRegression(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.data = str({
            "X1": [1, 2, 3, 4, 5],
            "X2": [2, 4, 6, 8, 10],
            "Y": [1.2, 2.4, 3.1, 4.0, 5.1]
        })

    def test_simple_regression(self):
        result = OLSRegression().perform_ols_regression(self.data, dependent_var="Y", independent_vars_str="X1")
        self.assertIn("R-squared", result)
        self.assertIn("Coefficients", result)

    def test_multiple_regression(self):
        result = OLSRegression().perform_ols_regression(self.data, dependent_var="Y", independent_vars_str="X1,X2")
        self.assertIn("R-squared", result)

    def test_missing_columns(self):
        with self.assertRaises(KeyError):
            OLSRegression().perform_ols_regression(self.data, dependent_var="Z", independent_vars_str="X1")

    def test_insufficient_data(self):
        data = str({
            "X1": [1],
            "Y": [2]
        })
        with self.assertRaises(ValueError):
            OLSRegression().perform_ols_regression(data, dependent_var="Y", independent_vars_str="X1")

    def test_mixed_data_types(self):
        data = str({
            "X1": [1, "b", 3, 4, "e"],
            "Y": [1, 2, 3, 4, 5]
        })
        with self.assertRaises(ValueError):
            OLSRegression().perform_ols_regression(data, dependent_var="Y", independent_vars_str="X1")

if __name__ == "__main__":
    unittest.main()
