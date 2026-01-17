from jsonschema import validate, ValidationError
import json

class JSONValidator:
    def validate_json(self, data_str: str, schema_str: str) -> str:
        try:
            data = json.loads(data_str)
            schema = json.loads(schema_str)
            validate(instance=data, schema=schema)
            return "True"
        except ValidationError:
            return "False"
        except Exception:
            return "Validation failed: Invalid JSON format"
