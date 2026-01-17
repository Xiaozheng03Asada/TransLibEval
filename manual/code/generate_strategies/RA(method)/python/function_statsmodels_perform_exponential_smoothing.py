from statsmodels.tsa.holtwinters import ExponentialSmoothing
import pandas as pd

class ExponentialSmoothingProcessor:
    
    def perform_exponential_smoothing(self, data: str, forecast_steps: int, seasonal: str, seasonal_periods: int) -> str:
       
        if forecast_steps <= 0:
            raise ValueError("forecast_steps must be greater than 0.")
        if seasonal not in ["add", "mul"]:
            raise ValueError("seasonal must be 'add' or 'mul'.")
        
        try:
            data = eval(data)
        except:
            raise TypeError("Data must be a valid list string representation.")
            
        if not isinstance(data, list):
            raise TypeError("Data must be a list.")

        try:
            data_series = pd.Series(data)

            model = ExponentialSmoothing(
                data_series,
                seasonal=seasonal,
                seasonal_periods=seasonal_periods
            ).fit()

            forecast = model.forecast(forecast_steps).tolist()

            return str({
                "Fitted Values": model.fittedvalues.tolist(),
                "Forecast": forecast,
                "Model Params": dict(model.params)
            })
        except Exception as e:
            raise Exception(f"Error in performing exponential smoothing: {e}")