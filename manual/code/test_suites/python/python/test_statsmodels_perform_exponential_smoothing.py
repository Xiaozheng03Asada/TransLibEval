
import unittest
from function_statsmodels_perform_exponential_smoothing import ExponentialSmoothingProcessor

class TestExponentialSmoothing(unittest.TestCase):

    def test_additive_seasonality(self):
        data = "[10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10, 10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10]"
        result = ExponentialSmoothingProcessor().perform_exponential_smoothing(data, forecast_steps=5, seasonal="add", seasonal_periods=12)
        self.assertIn("Forecast", result)

    def test_multiplicative_seasonality(self):
        data = "[10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10, 10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10]"
        result = ExponentialSmoothingProcessor().perform_exponential_smoothing(data, forecast_steps=5, seasonal="mul", seasonal_periods=12)
        self.assertIn("Forecast", result)

    def test_input_type_error(self):
        with self.assertRaises(TypeError):
            ExponentialSmoothingProcessor().perform_exponential_smoothing("invalid_input", forecast_steps=3, seasonal="add", seasonal_periods=12)

    def test_invalid_seasonal_type(self):
        data = "[10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10, 10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10]"
        with self.assertRaises(ValueError):
            ExponentialSmoothingProcessor().perform_exponential_smoothing(data, forecast_steps=5, seasonal="invalid", seasonal_periods=12)

    def test_invalid_forecast_steps(self):
        data = "[10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10, 10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10]"
        with self.assertRaises(ValueError):
            ExponentialSmoothingProcessor().perform_exponential_smoothing(data, forecast_steps=-1, seasonal="add", seasonal_periods=12)

if __name__ == "__main__":
    unittest.main()
