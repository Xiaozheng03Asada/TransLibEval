from pydantic import BaseModel, PositiveFloat, ValidationError
from typing import Annotated

class ProductValidator:
    def create_product(self, stock: int, price: float) -> str:
        StockType = Annotated[int, 0 <= 1000] 

        class Product(BaseModel):
            stock: Annotated[int, 0 <= 1000]
            price: PositiveFloat 

        try:
            product = Product(stock=stock, price=price)
            return str(product)
        except ValidationError as e:
            return str(e.errors())
