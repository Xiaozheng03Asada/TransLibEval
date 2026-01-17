import polars as pl


class PolarsExample:
    def filter_numbers(self, threshold: float, x: float, y: float = None, z: float = None) -> float:
        values = [val for val in [x, y, z] if val is not None]

        if not values:
            return None

        df = pl.DataFrame({"values": values})

        filtered_df = df.filter(pl.col("values") > threshold)

        if filtered_df.height == 0:
            return None

        return filtered_df.select(pl.col("values").max()).to_numpy()[0][0]
