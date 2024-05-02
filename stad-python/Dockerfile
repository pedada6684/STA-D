FROM python:3.9
WORKDIR /trans
COPY ./requirements.txt /trans/requirements.txt
RUN pip install --no-cache-dir --upgrade -r /trans/requirements.txt
COPY ./app /trans/app
COPY ./app/domain /trans/app/domain
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
