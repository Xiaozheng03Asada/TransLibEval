import unittest
from function_Schematics_model_import_data import Product

class TestProductModel(unittest.TestCase):

    def test_valid_data(self):
        product = Product()
        data = '{"name": "Laptop", "price": 1000}'
        expected = "{'name': 'Laptop', 'price': 1000, 'category': 'General'}"
        self.assertEqual(product.import_and_validate(data), expected)

    def test_missing_required_field(self):
        product = Product()
        data = '{"price": 1000}'
        result = product.import_and_validate(data)
        self.assertIn("name", result)

    def test_default_category(self):
        product = Product()
        data = '{"name": "Tablet", "price": 500}'
        native = product.import_and_validate(data)
        self.assertIn("'category': 'General'", native)

    def test_all_fields_provided(self):
        product = Product()
        data = '{"name": "Headphones", "price": 200, "category": "Electronics"}'
        expected = "{'name': 'Headphones', 'price': 200, 'category': 'Electronics'}"
        self.assertEqual(product.import_and_validate(data), expected)

    def test_partial_data_with_partial_true(self):
        product = Product()
        data = '{"name": "Camera"}'
        result = product.import_and_validate(data)
        self.assertIn("price", result)

if __name__ == "__main__":
    unittest.main()
