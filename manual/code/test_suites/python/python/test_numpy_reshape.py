import unittest
from function_numpy_reshape import ReshapeCalculator

class TestReshapeCalculator(unittest.TestCase):

    def test_valid_input(self):
        calc_reshape = ReshapeCalculator()
        result = calc_reshape.reshape(10, 5)
        self.assertIn("Price: 10", result)
        self.assertIn("Quantity: 5", result)
        self.assertIn("Total Amount: 50", result)

    def test_zero_value(self):
        calc_reshape = ReshapeCalculator()
        result = calc_reshape.reshape(0, 5)
        self.assertIn("Price: 0", result)
        self.assertIn("Quantity: 5", result)
        self.assertIn("Total Amount: 0", result)

    def test_negative_value(self):
        calc_reshape = ReshapeCalculator()
        result = calc_reshape.reshape(-10, 5)
        self.assertIn("Price: -10", result)
        self.assertIn("Quantity: 5", result)
        self.assertIn("Total Amount: -50", result)

    def test_float_value(self):
        calc_reshape = ReshapeCalculator()
        result = calc_reshape.reshape(10.5, 3.2)
        self.assertIn("Price: 10.5", result)
        self.assertIn("Quantity: 3.2", result)
        self.assertIn("Total Amount: 33.6", result)

    def test_default_values(self):
        calc_reshape = ReshapeCalculator()
        result = calc_reshape.reshape()
        self.assertIn("Price: 10", result)
        self.assertIn("Quantity: 5", result)
        self.assertIn("Total Amount: 50", result)

if __name__ == '__main__':
    unittest.main()
