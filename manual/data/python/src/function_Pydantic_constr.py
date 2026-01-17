from pydantic import BaseModel, EmailStr, ValidationError
from typing import Annotated

class UserProfileHandler:
    def create_user_profile(self, username: str, email: str) -> str:
        
        class UserProfile(BaseModel):
            username: Annotated[str, 
                         dict(min_length=3, max_length=20, regex=r'^[a-zA-Z0-9_]+$')]

            email: EmailStr  

        try:
            profile = UserProfile(username=username, email=email)
            return str(profile)
        except ValidationError as e:
            return str(e.errors())
