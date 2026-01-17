import numpy as np
from sklearn.impute import SimpleImputer

class SimpleImputerFunction:
    @staticmethod
    def quick_sort_from_string(input_str: str) -> str:
        if not input_str:
            return ""
        
        data = np.array([
            [float(item) if item != 'None' else np.nan for item in item.split(',')] 
            for item in input_str.split(';')
        ])

        flat_data = data.reshape(-1, 2)

        imputer = SimpleImputer(strategy='most_frequent')
        imputed_flat_data = imputer.fit_transform(flat_data)

        restored_data_3d = imputed_flat_data.reshape(data.shape)

        result_str = ';'.join([','.join(map(str, row)) for row in restored_data_3d])
        return result_str
