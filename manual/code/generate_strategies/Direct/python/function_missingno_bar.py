from missingno import bar
import pandas as pd
from io import StringIO

class MissingnoBar:
    def visualize_missing_data(self, csv_data: str) -> str:
        try:
            df = pd.read_csv(StringIO(csv_data))
            if df.empty:
                raise ValueError("Empty CSV data")
        except Exception as e:
            raise ValueError(f"Invalid CSV format: {e}")
        
        bar(df)  
        return "Missing data visualization generated."
