from pydantic import BaseModel


class classfication_request(BaseModel):
    videoId: int
    videoDescription: str


class classfication_response(BaseModel):
    videoId: int
    category: str
