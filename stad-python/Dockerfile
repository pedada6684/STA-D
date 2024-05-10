FROM python:3.9
WORKDIR /STAD-PYTHON
# TensorFlow .whl 파일 다운로드 및 설치
RUN wget https://storage.googleapis.com/tensorflow/linux/cpu/tensorflow_cpu-2.15.0-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl
RUN pip install --no-cache-dir tensorflow_cpu-2.15.0-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl

COPY ./requirements.txt /STAD-PYTHON/requirements.txt
RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir -r /STAD-PYTHON/requirements.txt
COPY ./app /STAD-PYTHON/app
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]