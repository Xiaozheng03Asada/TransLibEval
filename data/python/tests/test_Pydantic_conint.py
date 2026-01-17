import unittest
from function_Pydantic_conint import ProductValidator

class TestCreateProduct(unittest.TestCase):
    
    def test_valid_product(self):
        validator = ProductValidator()
        result = validator.create_product(500, 19.99)
        self.assertEqual(result, "stock=500 price=19.99")

    def test_zero_stock(self):
        validator = ProductValidator()
        result = validator.create_product(0, 5.00)
        self.assertEqual(result, "stock=0 price=5.0")

    def test_stock_at_upper_limit(self):
        validator = ProductValidator()
        result = validator.create_product(1000, 15.00)
        self.assertEqual(result, "stock=1000 price=15.0")

    def test_stock_at_lower_limit(self):
        validator = ProductValidator()
        result = validator.create_product(0, 20.00)
        self.assertEqual(result, "stock=0 price=20.0")

    def test_high_price(self):
        validator = ProductValidator()
        result = validator.create_product(100, 9999.99)
        self.assertEqual(result, "stock=100 price=9999.99")

if __name__ == "__main__":
    unittest.main()
