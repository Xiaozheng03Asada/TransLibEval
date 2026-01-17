from cerberus import Validator

class SimpleValidator:
    def validate_data(self, data_str, schema_str):
        try:
            data = eval(data_str)
            schema = eval(schema_str)
        except Exception:
            return "Error: Invalid string format for data or schema"
        validator = Validator(schema)
        is_valid = validator.validate(data)
        if is_valid:
            return "Valid data"
        else:
            return f"Invalid data: {str(validator.errors)}"
