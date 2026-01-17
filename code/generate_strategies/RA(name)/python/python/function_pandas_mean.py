import pandas as pd

class CalculateMean:

    def calculate_mean(self, value_A=None, value_B=None):
        if value_A is None or value_B is None:
            mean_A = None
            mean_B = None
        else:
            df = pd.DataFrame({
                'A': [value_A],
                'B': [value_B]
            })
            mean_A = df['A'].mean()
            mean_B = df['B'].mean()

        result = f"A: {mean_A}, B: {mean_B}"

        return result
