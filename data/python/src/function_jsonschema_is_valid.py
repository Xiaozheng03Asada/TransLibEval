from jsonschema import Draft7Validator
import json

class JSONValidator:
    def validate_json(self, data_str: str, schema_str: str) -> str:
        try:
            data = json.loads(data_str)
            schema = json.loads(schema_str)
            validator = Draft7Validator(schema)
            return "JSON is valid" if validator.is_valid(data) else "JSON is invalid"
        except Exception:
            return "JSON is invalid"
