from app.service.category_service import classfication_request, get_category  # res.py 모듈에서 translate 함수를 가져옴
from fastapi import APIRouter

category_router = APIRouter()

@category_router.post("/category/info")
def translate_text(request: list[classfication_request]):
    print(request)
    return get_category(request)
