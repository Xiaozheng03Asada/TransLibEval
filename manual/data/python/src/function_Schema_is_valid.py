from schema import Schema, Use, Optional, Or


class UserValidator:
    def validate_user(self, name, age, email=None):
        schema = Schema({
            'name': str,
            'age': Use(int, lambda n: n > 0),
            Optional('email'): Or(None, lambda e: isinstance(e, str) and '@' in e)
        })
        data = {'name': name, 'age': age, 'email': email}
        if schema.is_valid(data):
            return "Valid data"
        else:
            return "Invalid data"
