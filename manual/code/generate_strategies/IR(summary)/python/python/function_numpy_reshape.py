import numpy as np


class ReshapeCalculator:

    def reshape(self, price=None, quantity=None):
        if price is None or quantity is None:
            price = 10
            quantity = 5

        data = np.array([price, quantity])

        reshaped_data = data.reshape(1, 2)

        total_amount = reshaped_data[0][0] * reshaped_data[0][1]

        result = f"Price: {reshaped_data[0][0]}, Quantity: {reshaped_data[0][1]}, Total Amount: {total_amount}"

        return result
