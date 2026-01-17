import numpy as np

class NumpyExample:
    def check_number(self, x: int) -> str:
        return np.where(x > 0, "positive", np.where(x < 0, "negative", "zero"))
