import missingno as msno
import pandas as pd

class MissingnoDendrogram:
    def create_dendrogram(self, data_str: str) -> str:
        try:
            data = pd.read_csv(pd.compat.StringIO(data_str))
            msno.dendrogram(data)
            return "Dendrogram created successfully"
        except Exception:
            return "failed"
