import missingno as msno
import pandas as pd
import io

class DataHeatmap:
    def generate_heatmap(self, data: str) -> str:
        try:
            if data is None:
                return "An error occurred: Input data is None"

            df = pd.read_csv(io.StringIO(data), sep=",")
            
            if df.empty:
                return "No data in the file"

            msno.heatmap(df)
            return "Heatmap generated successfully"

        except pd.errors.EmptyDataError:
            return "No data in the file"
        except pd.errors.ParserError:
            return "Invalid file format"
        except IndexError:  # 解析失败时可能抛出 IndexError
            return "Invalid file format"
        except Exception as e:
            return f"An error occurred: {str(e)}"
