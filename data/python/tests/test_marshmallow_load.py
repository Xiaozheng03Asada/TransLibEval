import unittest
from function_marshmallow_load import ProductSchema

class TestDeserializeProductData(unittest.TestCase):
    
    def setUp(self):
        self.schema = ProductSchema()

    def test_valid_data(self):
        product_data = '{"name": "Laptop", "price": 999.99, "in_stock": true}'
        result = self.schema.deserialize_product_data(product_data)
        self.assertEqual(result, '{"name": "Laptop", "price": 999.99, "in_stock": true}')

    def test_missing_required_fields(self):
        product_data = '{"price": 499.99}'
        result = self.schema.deserialize_product_data(product_data)
        self.assertEqual(result, "Error: invalid input")

    def test_default_field_value(self):
        product_data = '{"name": "Tablet", "price": 199.99}'
        result = self.schema.deserialize_product_data(product_data)
        self.assertEqual(result, '{"name": "Tablet", "price": 199.99, "in_stock": true}')

    def test_invalid_data_type(self):
        product_data = '{"name": "Chair", "price": "forty"}'
        result = self.schema.deserialize_product_data(product_data)
        self.assertEqual(result, "Error: invalid input")

    def test_product_with_only_name_and_price(self):
        product_data = '{"name": "Phone", "price": 799.99}'
        result = self.schema.deserialize_product_data(product_data)
        self.assertEqual(result, '{"name": "Phone", "price": 799.99, "in_stock": true}')

if __name__ == "__main__":
    unittest.main()
