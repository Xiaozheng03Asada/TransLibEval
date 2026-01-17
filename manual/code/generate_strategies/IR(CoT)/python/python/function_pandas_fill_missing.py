import pandas as pd


class FillMissingValues:

    def fill_missing_values(self, value_A, value_B):
        data = {
            'A': [value_A],
            'B': [value_B]
        }
        df = pd.DataFrame(data)

        if value_A is None and value_B is None:
            result = f"A: nan, B: nan"
        else:
            df = df.fillna(df.mean())
            result = f"A: {str(df['A'][0])}, B: {str(df['B'][0])}"

        return result
