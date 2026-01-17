import unittest
from function_Pydantic_root_validator import Order

class TestCreateOrder(unittest.TestCase):
    def test_valid_order(self):
        handler = Order()
        result = handler.check_discount_for_large_order(5, 100.0, 0.0)
        self.assertEqual(result, "quantity=5 price=100.0 discount=0.0")

    def test_order_with_large_quantity_without_discount(self):
        handler = Order()
        result = handler.check_discount_for_large_order(15, 100.0, 0.0)
        self.assertIsInstance(result, str)
        self.assertIn('Discount must be greater than 0 for orders with quantity greater than 10', result)

    def test_order_with_discount_but_negative_price(self):
        handler = Order()
        result = handler.check_discount_for_large_order(5, -50.0, 10.0)
        self.assertIsInstance(result, str)
        self.assertIn('Price must be positive when discount is applied', result)

    def test_order_with_large_quantity_and_discount(self):
        handler = Order()
        result = handler.check_discount_for_large_order(15, 100.0, 10.0)
        self.assertEqual(result, "quantity=15 price=100.0 discount=10.0")

    def test_valid_order_without_discount(self):
        handler = Order()
        result = handler.check_discount_for_large_order(5, 50.0, 0.0)
        self.assertEqual(result, "quantity=5 price=50.0 discount=0.0")

if __name__ == "__main__":
    unittest.main()
