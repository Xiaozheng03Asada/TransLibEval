from schematics.types import BaseType

class PositiveIntegerType(BaseType):

    def validate(self, value: str) -> str:
        try:
            value = int(value) 
        except ValueError:
            return "Value must be an integer."
        
        if value <= 0:
            return "Value must be a positive integer."
        
        return "Validation successful."
