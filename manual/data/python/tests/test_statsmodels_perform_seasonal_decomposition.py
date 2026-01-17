import unittest
from function_statsmodels_perform_seasonal_decomposition import SeasonalDecomposition

class TestSeasonalDecomposition(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.additive_data = "10.248357, 10.248557, 10.248757, 10.248957, 10.249157, 10.249357, 10.249557, 10.249757, 10.249957, 10.250157"

    def test_additive_decomposition(self):
        result = SeasonalDecomposition().perform_seasonal_decomposition(self.additive_data, model="additive", period=12)
        self.assertIn("Trend", result)
        self.assertIn("Seasonal", result)
        self.assertIn("Residual", result)

    def test_multiplicative_decomposition(self):
        result = SeasonalDecomposition().perform_seasonal_decomposition(self.additive_data, model="multiplicative", period=12)
        self.assertIn("Trend", result)
        self.assertIn("Seasonal", result)
        self.assertIn("Residual", result)

    def test_invalid_model_type(self):
        with self.assertRaises(ValueError):
            SeasonalDecomposition().perform_seasonal_decomposition(self.additive_data, model="invalid", period=12)

    def test_invalid_period(self):
        with self.assertRaises(ValueError):
            SeasonalDecomposition().perform_seasonal_decomposition(self.additive_data, model="additive", period=-1)

    def test_non_string_input(self):
        with self.assertRaises(TypeError):
            SeasonalDecomposition().perform_seasonal_decomposition(12345, model="additive", period=12)

if __name__ == "__main__":
    unittest.main()
