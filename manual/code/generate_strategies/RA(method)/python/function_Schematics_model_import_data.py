from schematics.models import Model
from schematics.types import StringType, IntType

class Product(Model):
    name = StringType(required=True)
    price = IntType(required=True, min_value=0)
    category = StringType(default="General")

    def import_and_validate(self, data: str) -> str:
        try:
            data_dict = eval(data)
            self.import_data(data_dict, partial=False)
            self.validate()
            return str(self.to_native())
        except Exception as e:
            return f"error: {str(e)}"
