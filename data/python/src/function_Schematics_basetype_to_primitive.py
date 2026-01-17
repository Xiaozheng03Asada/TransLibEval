from schematics.types import BaseType

class IntegerListType(BaseType):

    def to_primitive(self, value: str) -> str:
        if not value.startswith("[") or not value.endswith("]"):
            raise ValueError("Value must be a string representing a list.")
        
        try:
            value = eval(value)
            if not isinstance(value, list):
                raise ValueError("Value must be a list.")
            for item in value:
                if not isinstance(item, int):
                    raise ValueError("All items in the list must be integers.")
        except:
            raise ValueError("Value must be a valid list of integers in string format.")
        
        return str(value)
