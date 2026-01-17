from cerberus import Validator


class NumberComparer:
    def compare_numbers(self, num1, num2):
        schema = {
            'num1': {'type': 'number'},
            'num2': {'type': 'number'}
        }
        v = Validator(schema)
        data = {'num1': num1, 'num2': num2}
        if not v.validate(data):
            return f"Error: Invalid input. {v.errors}"
        if num1 > num2:
            return "Greater"
        elif num1 < num2:
            return "Smaller"
        else:
            return "Equal"
