import dask.array as da

class CalculateMeanFunction:
    def calculate_mean(self, data_str: str, chunks: int) -> str:
        try:
            data = list(map(float, data_str.split(",")))
            dask_array = da.from_array(data, chunks=chunks)
            block_mean = dask_array.mean().compute()
            return str(block_mean)
        except Exception as e:
            return f"Error: {e}"
