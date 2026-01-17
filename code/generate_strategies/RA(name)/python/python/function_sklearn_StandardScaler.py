from sklearn.preprocessing import StandardScaler
import numpy as np

class StandardScalerFunction:
    @staticmethod
    def quick_sort_from_string(input_str: str) -> str:
        if not input_str:
            return ""
        
        data = np.array([
            [float(item) for item in item.split(',')] 
            for item in input_str.split(';')
        ])
        
        if data.ndim != 2:
            raise ValueError("Input data must be a 2-dimensional numpy array.")
        
        if not np.issubdtype(data.dtype, np.number) or np.any(np.isnan(data)):
            raise TypeError("Input data must be a numeric array without NaN values.")
        
        scaler = StandardScaler()
        scaler.fit(data)
        transformed_data = scaler.transform(data)

        result_str = ';'.join([','.join(map(str, row)) for row in transformed_data])
        return result_str
