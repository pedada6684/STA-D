from pydantic import BaseModel


class classfication_request(BaseModel):
    video_id: int
    video_discription: str


class classfication_response(BaseModel):
    video_id: int
    category: str
