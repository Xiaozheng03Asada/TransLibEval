from pydantic import BaseModel, model_validator, ValidationError

class Order:
    def check_discount_for_large_order(self, quantity: int, price: float, discount: float = 0.0) -> str:
        class OrderModel(BaseModel):
            quantity: int
            price: float
            discount: float = 0.0  

            @model_validator(mode='after')  
            def check_discount_for_large_order(cls, values):
                if values.quantity > 10 and values.discount == 0:
                    raise ValueError('Discount must be greater than 0 for orders with quantity greater than 10')
                
                if values.discount > 0 and values.price <= 0:
                    raise ValueError('Price must be positive when discount is applied')
                
                return values

        try:
            order = OrderModel(quantity=quantity, price=price, discount=discount)
            return str(order)
        except ValidationError as e:
            return str(e.errors())
