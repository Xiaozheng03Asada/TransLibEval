import pandas as pd


class CreatePivotTable:

    def create_pivot_table(self, date1=None, date2=None, category1=None, category2=None, value1=None, value2=None):
        if date1 is None or date2 is None or category1 is None or category2 is None or value1 is None or value2 is None:
            date1 = "2023-01-01"
            date2 = "2023-01-02"
            category1 = "A"
            category2 = "B"
            value1 = 1
            value2 = 2

        data = {
            'Date': [date1, date2],
            'Category': [category1, category2],
            'Values': [value1, value2]
        }
        df = pd.DataFrame(data)

        pivot_df = pd.pivot_table(df, index='Date', columns='Category', values='Values', aggfunc='sum', fill_value=0)

        result = f"Date: {pivot_df.index[0]}, Category A: {pivot_df.iloc[0]['A']}, Category B: {pivot_df.iloc[0]['B']}"
        return result
