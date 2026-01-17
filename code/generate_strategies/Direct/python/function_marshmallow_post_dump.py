from marshmallow import Schema, fields, post_dump, ValidationError
import json

class UserSchema:
    def serialize_user_data(self, user_data: str) -> str:
        class UserSchemaInner(Schema):
            age = fields.Int(required=True)
            score = fields.Float(required=True)

            @post_dump
            def format_output(self, data, **kwargs):
                if not isinstance(data, dict):
                    raise ValidationError("Output must be a dictionary.")
                return {"result": data}

        schema = UserSchemaInner()
        try:
            parsed_data = json.loads(user_data)
            serialized_data = schema.dump(parsed_data)
            return json.dumps(serialized_data)
        except (ValidationError, json.JSONDecodeError):
            return "Error: invalid input"
