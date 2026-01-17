import polars as pl


class PolarsExample:
    def compute_sum(self, x: float, y: float = None, z: float = None) -> float:
        values = [val for val in [x, y, z] if val is not None]

        if not values:
            return 0.0
        df = pl.DataFrame({"values": values})
        return df.select(pl.col("values").sum()).to_numpy()[0][0]
