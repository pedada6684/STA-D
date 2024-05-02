from pydantic import BaseModel


class classfication_request(BaseModel):
    videoid: str
    video_discription: str


class classfication_response(BaseModel):
    video_id: str
    category: str
