from marshmallow import Schema, fields, pre_load, ValidationError
import json

class ProductProcessor:
    def process_product_data(self, product_data: str) -> str:
        class ProductSchema(Schema):
            name = fields.Str(required=True)
            price = fields.Float(required=True)
            in_stock = fields.Bool(missing=True)

            @pre_load
            def convert_name_to_uppercase(self, data, **kwargs):
                if 'name' in data:
                    if isinstance(data['name'], str):  
                        data['name'] = data['name'].upper()
                    else:
                        raise ValidationError("'name' must be a string.")
                return data

        schema = ProductSchema()
        try:
            parsed_data = json.loads(product_data)
            result = schema.load(parsed_data)
            return json.dumps(result)
        except (ValidationError, json.JSONDecodeError):
            return "Error: invalid input"
