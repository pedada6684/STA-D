from pydantic import BaseModel


class user_category_request(BaseModel):
    userId: int
    text: str


class user_category_response(BaseModel):
    userId: int
    category: str
