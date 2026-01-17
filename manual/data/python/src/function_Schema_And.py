from schema import Schema, And

class StringValidator:
    def validate_user_input(self, username, password):
        schema = Schema({
            'username': And(str, len),
            'password': And(str, len, lambda s: len(s) >= 8)
        })
        try:
            result = schema.validate({'username': username, 'password': password})
            if result:
                return f"Validation passed: username = {username}, password = {password}"
            else:
                return "Validation failed"
        except Exception as e:
            return f"Validation failed"
