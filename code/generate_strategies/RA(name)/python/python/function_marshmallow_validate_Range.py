from marshmallow import Schema, fields, ValidationError, validate
import json

class UserValidator:
    def validate_user_data(self, user_data: str) -> str:
        class UserSchema(Schema):
            age = fields.Integer(required=True, validate=validate.Range(min=0, max=120))
            score = fields.Float(required=True, validate=validate.Range(min=0.0, max=100.0))

        schema = UserSchema()
        try:
            parsed_data = json.loads(user_data)
            result = schema.load(parsed_data)
            return json.dumps(result)
        except (ValidationError, json.JSONDecodeError):
            return "Error: invalid input"
