import dask.dataframe as dd
import pandas as pd

class ComputePartitionMeansFunction:
    def compute_partition_means(self, data_str: str, column: str) -> str:
        try:
            data = pd.read_csv(pd.io.common.StringIO(data_str))
            if column not in data.columns:
                return "Error"

            dask_df = dd.from_pandas(data, npartitions=2)
            partition_means = dask_df.map_partitions(lambda df: df[column].mean()).compute()
            return ",".join(map(str, partition_means.tolist()))
        except Exception:
            return "Error"
