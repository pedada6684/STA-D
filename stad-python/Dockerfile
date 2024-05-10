FROM python:3.9.7
WORKDIR /STAD-PYTHON
COPY ./requirements.txt /STAD-PYTHON/requirements.txt
RUN pip install --no-cache-dir --upgrade -r /STAD-PYTHON/requirements.txt
COPY ./app /STAD-PYTHON/app
COPY ./app/domain /STAD-PYTHON/app/domain
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]