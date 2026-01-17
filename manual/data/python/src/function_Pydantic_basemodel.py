from pydantic import BaseModel, ValidationError

class UserValidator:
    def create_user(self, name: str, age: int, email: str) -> str:
        class User(BaseModel):
            name: str
            age: int
            email: str

        try:
            user = User(name=name, age=age, email=email)
            return str(user)
        except ValidationError as e:
            return str(e.errors())
