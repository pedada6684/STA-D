from fastapi import APIRouter
from app.dto.recommend_dto import request_recommendInfo, response_recommend
from app.service.recommend_service import cos_sim

recommend_router = APIRouter()


@recommend_router.post("/recommend/info", response_model=list[response_recommend])
def recommend_item(requests: request_recommendInfo):
    result = cos_sim(requests)

    response = []
    for (key, value) in result:
        response.append(response_recommend(videoId=key))

    return response[:15]
