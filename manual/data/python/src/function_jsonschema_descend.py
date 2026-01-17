from jsonschema import exceptions

class JSONValidator:
    def validate_json(self, data_str: str, schema_str: str) -> str:
        try:
            import json
            data = json.loads(data_str)
            schema = json.loads(schema_str)

            if not isinstance(data, dict):
                return "Validation failed"

            type_map = {
                "string": str,
                "integer": int,
                "boolean": bool,
                "object": dict,
                "array": list,
                "number": (int, float)
            }

            for key, value in data.items():
                field_schema = schema.get("properties", {}).get(key)
                if field_schema:
                    if "type" in field_schema and not isinstance(value, type_map.get(field_schema["type"], object)):
                        return "Validation failed"
                    if isinstance(value, dict):
                        nested_result = self.validate_json(json.dumps(value), json.dumps(field_schema))
                        if nested_result != "JSON is valid":
                            return "Validation failed"
                elif key not in schema.get("properties", {}):
                    return "Validation failed"

            for req in schema.get("required", []):
                if req not in data:
                    return "Validation failed"

            return "JSON is valid"
        except Exception:
            return "Validation failed"
