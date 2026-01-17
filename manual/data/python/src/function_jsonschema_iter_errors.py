from jsonschema import Draft7Validator
import json

class JSONValidator:
    def validate_json(self, data_str: str, schema_str: str) -> str:
        try:
            data = json.loads(data_str)
            schema = json.loads(schema_str)
            validator = Draft7Validator(schema)
            errors = list(validator.iter_errors(data))
            return f"Validation failed: {errors[0].message}" if errors else "JSON is valid"
        except Exception:
            return "Validation failed: Invalid JSON format"
