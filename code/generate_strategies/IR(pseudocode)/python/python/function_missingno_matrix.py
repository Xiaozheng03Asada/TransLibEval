
import missingno as msno
import pandas as pd
import io
import base64

class DataMissingVisualizer:
    def visualize_missing_data(self, csv_data: str) -> str:
        df = pd.read_csv(io.StringIO(csv_data))
        img_buf = io.BytesIO()
        msno.bar(df, figsize=(5, 3)).figure.savefig(img_buf, format="png")
        img_buf.seek(0)
        return base64.b64encode(img_buf.getvalue()).decode("utf-8")
