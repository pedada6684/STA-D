from fastapi import FastAPI
import uvicorn

from app.router.category_route import category_router

app = FastAPI()

# # API Router를 등록합니다.
app.include_router(category_router)

if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8000)
