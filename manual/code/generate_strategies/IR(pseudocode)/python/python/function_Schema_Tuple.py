from schema import Schema, And, Use, Optional, Or


class ValidateTuple:
    def validate(self, input_str):
        try:
            parts = input_str.split(',')
            if len(parts) != 2:
                return "Invalid input: Must be a pair of values"
            first, second = parts[0].strip(), parts[1].strip()
            if not first.isdigit():
                return "Invalid input: First value must be an integer"
            return f"Valid tuple: ({first}, {second})"
        except Exception as e:
            return f"Error: {str(e)}"
