from marshmallow import Schema, fields, ValidationError
import json

class ProductSchema(Schema):
    name = fields.Str(required=True)
    price = fields.Float(required=True)
    in_stock = fields.Bool(missing=True)

    def deserialize_product_data(self, data: str) -> str:
        
        try:
            parsed_data = json.loads(data)
            result = self.load(parsed_data)
            return json.dumps(result)
        except (ValidationError, json.JSONDecodeError):
            return "Error: invalid input"
