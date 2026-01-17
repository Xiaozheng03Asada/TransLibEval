import numpy as np


class SortCalculator:

    def sort(self, value1=None, value2=None, value3=None):
        if value1 is None or value2 is None or value3 is None:
            value1 = 10
            value2 = 5
            value3 = 15

        data = np.array([value1, value2, value3])

        sorted_data = np.sort(data)

        result = f"Sorted Values: {sorted_data[0]}, {sorted_data[1]}, {sorted_data[2]}"

        return result
