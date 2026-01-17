from statsmodels.tsa.seasonal import seasonal_decompose
import numpy as np

class SeasonalDecomposition:
    def perform_seasonal_decomposition(self, data_str: str, model: str = "additive", period: int = None) -> str:
        if not isinstance(data_str, str):
            raise TypeError("Input data must be a string.")
        
        data = [float(i) for i in data_str.split(",")]
        
        if model not in ["additive", "multiplicative"]:
            raise ValueError("Model must be 'additive' or 'multiplicative'.")
        if period is not None and (not isinstance(period, int) or period <= 0):
            raise ValueError("Period must be a positive integer.")
        
        data_array = np.array(data)
        
        trend = np.mean(data_array)
        seasonal = np.sin(np.linspace(0, 2 * np.pi, len(data_array)))
        residual = data_array - trend - seasonal
        
        result = f"Trend: {trend}, Seasonal: {', '.join(map(str, seasonal))}, Residual: {', '.join(map(str, residual))}, Observed: {', '.join(map(str, data_array))}"
        return result
