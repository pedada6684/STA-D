from app.service.category_service import request_category, get_category  # res.py 모듈에서 translate 함수를 가져옴
from fastapi import APIRouter

category_router = APIRouter()

@category_router.post("/category/info")
def translate_text(request: list[request_category]):
    return get_category(request)
