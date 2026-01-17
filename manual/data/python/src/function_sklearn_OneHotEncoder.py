from sklearn.preprocessing import OneHotEncoder
import numpy as np

class OneHotEncoderFunction:
    @staticmethod
    def test_one_hot_encoding(data: str) -> str:
        if not isinstance(data, str):
            raise TypeError("Input data must be a string.")
        if len(data.split()) < 2:
            raise IndexError("The input data has incorrect structure.")

        try:
            categorical_data = data.split()[0]
            numeric_data = int(data.split()[1])
        except IndexError:
            raise IndexError("Data structure is incorrect. Maybe missing categorical or numeric part.")

        encoder = OneHotEncoder()
        encoded_categorical_data = encoder.fit_transform(np.array([[categorical_data]]))
        encoded_value = encoded_categorical_data.toarray()[0][0]

        final_data = f"{numeric_data},{encoded_value}"
        return final_data
