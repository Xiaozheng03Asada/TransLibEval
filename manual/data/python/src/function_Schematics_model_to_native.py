from schematics.models import Model
from schematics.types import StringType, IntType
from schematics.exceptions import DataError

class Person:
    def get_native_representation(self, data: str) -> str:
        if not data or len(data.split(',')) != 2:
            return "error: Invalid input format"
        
        data = data.split(",")
        data_dict = {"name": data[0], "age": int(data[1])}
        
        class PersonModel(Model):
            name = StringType(required=True)
            age = IntType(required=True, min_value=0)
            city = StringType(default="Unknown")
        
        person = PersonModel(data_dict)
        try:
            person.validate()
            return f"{person.name} {person.age} {person.city}"
        except DataError as e:
            return f"error: {e.messages}"
