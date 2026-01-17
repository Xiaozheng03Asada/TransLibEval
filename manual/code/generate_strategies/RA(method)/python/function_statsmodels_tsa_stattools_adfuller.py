from statsmodels.tsa.stattools import adfuller

class SeasonalDecomposition:
    def perform_adf_test(self, time_series: str, significance_level: float = 0.05) -> str:
        if not isinstance(time_series, str):
            raise TypeError("Input time_series must be a string.")
        
        try:
            time_series = [float(i) for i in time_series.split(",")]
        except ValueError:
            raise ValueError("Time series string must contain valid numeric values separated by commas.")
        
        if len(time_series) < 10:
            raise ValueError("Time series must have at least 10 observations.")
        
        result = adfuller(time_series, autolag='AIC')
        test_statistic, p_value, lags_used, n_obs, critical_values, ic_best = result
        
        conclusion = "Stationary" if p_value < significance_level else "Non-Stationary"
        
        return f"Test Statistic: {test_statistic}, P-Value: {p_value}, Conclusion: {conclusion}"
