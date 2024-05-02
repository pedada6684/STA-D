import tensorflow as tf

from gensim.models import Word2Vec
import numpy as np

from app.common.preprocess import preprocess
from app.dto.category_dto import classfication_request, classfication_response
import logging

def token_to_idx(tokenized_document, unk_idx):
    logging.debug(f"Received document: {tokenized_document}")
    if isinstance(tokenized_document, np.ndarray):
        tokens = tokenized_document.tolist()
        logging.debug("Converted numpy array to list.")
    elif isinstance(tokenized_document, str):
        tokens = tokenized_document.split(' ')
        logging.debug("Split string into tokens.")
    else:
        tokens = list(tokenized_document)
        logging.debug("Handled as iterable, converted to list.")

    idx_list = [w2v_model.wv.index_to_key.index(token) if token in w2v_model.wv.index_to_key else unk_idx for token in tokens]
    return idx_list


def sentence_to_sequence(sentence):
    sequences = [token_to_idx(sentence, UNK_IDX)]
    sequences = tf.keras.utils.pad_sequences(sequences,
                                             padding='post',
                                             truncating='post',
                                             maxlen=MAX_LEN,
                                             value=len(w2v_model.wv.index_to_key) + 1)
    return sequences


def predict(sentence):
    result = model.predict(sentence_to_sequence(sentence))
    return label[result.argmax()]


def get_category(requests: list[classfication_request]):
    responses = []
    for request in requests:
        text = preprocess(request.video_discription)
        print(text)
        result = predict(sentence_to_sequence(text))
        response = classfication_response(video_id=request.video_id, category=result)
        responses.append(response)

    return responses

w2v_path = './app/models/word2vec.model'
category_path = './app/models/category.h5'

label = ['다이어트식품', '주변기기', '자동차용품', '카페트/러그', '여성의류', '음향가전', '공구', '화방용품',
       '악기', 'PC액세서리', '장류', 'DIY자재/용품', '태블릿PC액세서리', '바디케어', '완구/매트',
       '수납/정리용품', '식용유/오일', '물리치료/저주파용품', '주방용품', '건강식품', '축산물', '클렌징',
       '뷰티소품', '스쿼시', '스킨케어', '헤어케어', '헤어스타일링', '낚시', '농산물', '패션소품', '수영',
       '모자', '여성언더웨어/잠옷', '양말', '오토바이/스쿠터', '유가공품', '해외여행', '문구/사무용품',
       '국내여행/체험', '생활가전', '자동차기기', '반려동물', '여성신발', '생활용품', '주방가전', '음료',
       '이미용가전', '서재/사무용가구', '유모차', '주얼리', '휴대폰액세서리', '소독/살균용품', '수집품',
       'PC부품', '시계', '생활편의', '헬스', '이유식용품', '잼/시럽', '야구', '김치', '욕실용품',
       '계절가전', '과자/베이커리', '검도', '게임기/타이틀', '스포츠액세서리', '소프트웨어', '저장장치',
       '인테리어소품', '냉동/간편조리식품', '여성가방', '침구세트', '노트북', '댄스', '수산물',
       '헤어액세서리', '마스크/팩', '멀티미디어장비', '눈건강용품', '정원/원예용품', '아기간식', '지갑',
       '반찬', '캠핑', '수예', '주류', '자전거', '보호용품', '영상가전', '골프', '교구', '장갑',
       '카메라/캠코더용품', '재활운동용품', '소스/드레싱', '솜류', '라면/면류', '음반', '학습기기',
       '가루/분말류', '홈데코', '여행용가방/소품', '커튼/블라인드', '권투', '세탁용품', '침구단품',
       '조미료', '네트워크장비', 'DVD', '건강관리용품', '유아동의류', '스킨/바디용품', '색조메이크업',
       '요가/필라테스', '구강청결용품', '임산부용품', '등산', '제과/제빵재료', '원예/식물', '출산/돌기념품',
       '신생아의류', '국내렌터카', '침실가구', '남성의류', '유아침구', '종교', '선글라스/안경테', '목욕용품',
       '외출용품', '유아가구', '유아세제', '축구', '유아동언더웨어/잠옷', '실버용품', '안전용품', '밀키트',
       '모니터주변기기', '인형', '임부복', '거실가구', '유아동잡화', '블루레이', '의료용품',
       '아동/주니어가구', '유아발육용품', '남성가방', '수납가구', '주방가구', '마라톤용품', '신발용품',
       '족구', '예체능레슨', '노트북액세서리', '테니스', '벨트', '발건강용품', '관상어용품', '향수',
       '스키/보드', '구강위생용품', '남성화장품', '스케이트/보드/롤러', '볼링', '아웃도어가구',
       '자기계발/취미 레슨', '베이스메이크업', '안마용품', '수련용품', '통조림/캔', '청소용품',
       '위생/건강용품', '분유', '휴대폰', '수유용품', '좌욕/좌훈용품', '배드민턴', 'PC', '탁구',
       '무술용품', '건강측정용품', '스킨스쿠버', '물티슈', '광학기기/용품', '기저귀', '모니터', '농구',
       '냉온/찜질용품', '당뇨관리용품', '선케어', '수영복/용품', '유아동 주얼리', '이유식',
       '남성언더웨어/잠옷', '카시트', '당구용품', '남성신발', '네일케어', '태블릿PC', '배구',
       '원데이클래스', '인라인스케이트', '순금', '홈케어서비스', '기타스포츠용품', '']

w2v_model = Word2Vec.load(w2v_path)
model = tf.keras.models.load_model(category_path)

UNK_IDX = len(w2v_model.wv.index_to_key)
MAX_LEN = 32

