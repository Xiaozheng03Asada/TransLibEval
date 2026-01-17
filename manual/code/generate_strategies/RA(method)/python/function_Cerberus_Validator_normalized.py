from cerberus import Validator


class DataNormalizer:
    def normalize_data(self, data_str):
        if not isinstance(data_str, str):
            return "Error: Input must be a string"
        try:
            data = eval(data_str)
        except Exception:
            return "Error: Invalid string format for data"

        schema = {
            'name': {'type': 'string', 'default': 'Unknown'},
            'age': {'type': 'integer', 'default': 18}
        }
        v = Validator(schema)
        normalized_data = v.normalized(data)
        return str(normalized_data)
