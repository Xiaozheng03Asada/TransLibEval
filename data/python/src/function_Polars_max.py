import polars as pl

class PolarsExample:
    def compute_max(self, x: float, y: float = None, z: float = None) -> float:
        values = [val for val in [x, y, z] if val is not None]

        if not values:
            return None
        df = pl.DataFrame({"values": values})
        return df.select(pl.col("values").max()).to_numpy()[0][0]
