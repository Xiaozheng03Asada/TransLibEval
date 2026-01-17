from cerberus import Validator

class DataValidator:
    def validate_data(self, data_str):
        data = eval(data_str)
        schema = {
            'name': {'type': 'string', 'minlength': 3},
            'age': {'type': 'integer', 'min': 18, 'max': 100}
        }
        v = Validator(schema)
        validator_schema = v.schema
        if not v.validate(data):
            return f"Error: Invalid data - {v.errors}"
        return f"Valid data: {validator_schema}"
