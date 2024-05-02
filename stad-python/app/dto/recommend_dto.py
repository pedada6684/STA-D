from pydantic import BaseModel


class video(BaseModel):
    video_id: str
    video_grade: str
    video_transcript: str


class request_recommendInfo(BaseModel):
    concern: str
    videoDtoList: list[video]


class response_recommend(BaseModel):
    videoId: str
