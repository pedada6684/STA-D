from pydantic import BaseModel


class request_category(BaseModel):
    video_id: str
    video_transcript: str
    video_grade: str


class response_category(BaseModel):
    video_id: str
    category: str
