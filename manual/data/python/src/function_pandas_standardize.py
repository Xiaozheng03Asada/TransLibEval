import pandas as pd


class StandardizeData:

    def standardize(self, value_A=None, value_B=None):
        if value_A is None or value_B is None:
            return "A: None, B: None"

        df = pd.DataFrame({
            'A': [value_A],
            'B': [value_B]
        })

        mean_A = df['A'].mean()
        std_A = df['A'].std()
        mean_B = df['B'].mean()
        std_B = df['B'].std()

        standardized_A = (value_A - mean_A) / std_A if std_A != 0 else 0
        standardized_B = (value_B - mean_B) / std_B if std_B != 0 else 0

        standardized_A = 0 if pd.isna(standardized_A) else standardized_A
        standardized_B = 0 if pd.isna(standardized_B) else standardized_B

        result = f"A: {standardized_A}, B: {standardized_B}"

        return result
