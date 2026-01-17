import unittest
from function_marshmallow_pre_load import ProductProcessor

class TestProductProcessor(unittest.TestCase):

    def setUp(self):
        self.processor = ProductProcessor()

    def test_pre_load_conversion(self):
        product_data = '{"name": "tablet", "price": 199.99}'
        result = self.processor.process_product_data(product_data)
        self.assertIn('"name": "TABLET"', result)
        self.assertIn('"price": 199.99', result)

    def test_invalid_data_type(self):
        product_data = '{"name": 12345, "price": 199.99}'  
        result = self.processor.process_product_data(product_data)
        self.assertEqual(result, "Error: invalid input")

    def test_invalid_price(self):
        product_data = '{"name": "tablet", "price": "not_a_number"}'
        result = self.processor.process_product_data(product_data)
        self.assertEqual(result, "Error: invalid input")

    def test_missing_name_field(self):
        product_data = '{"price": 199.99, "in_stock": true}'
        result = self.processor.process_product_data(product_data)
        self.assertEqual(result, "Error: invalid input")

    def test_missing_price_field(self):
        product_data = '{"name": "tablet", "in_stock": true}'
        result = self.processor.process_product_data(product_data)
        self.assertEqual(result, "Error: invalid input")

if __name__ == '__main__':
    unittest.main()
