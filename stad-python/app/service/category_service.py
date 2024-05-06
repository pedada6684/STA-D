import tensorflow as tf

from gensim.models import Word2Vec
import numpy as np

from app.common.preprocess import preprocess
from app.dto.category_dto import classfication_request, classfication_response
from app.dto.user_dto import user_category_request, user_category_response
import logging

def token_to_idx(tokenized_document, unk_idx):
    if isinstance(tokenized_document, np.ndarray):
        tokens = tokenized_document.tolist()
    elif isinstance(tokenized_document, str):
        tokens = tokenized_document.split(' ')
    else:
        tokens = list(tokenized_document)

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
        text = request.videoDescription
        result = predict(text)
        response = classfication_response(videoId=request.videoId, category=result)
        print(response)
        responses.append(response)

    return responses

def predict_top3_categories(request: user_category_request):
    sequence = sentence_to_sequence(request.text)
    prediction = model.predict(sequence)
    
    user_id = request.userId 
    top3_indices = np.argsort(prediction[0])[-3:][::-1]
    top3_categories = [label[idx] for idx in top3_indices]
    
    responses = [user_category_response(userId=user_id, category=cat) for cat in top3_categories]
    
    return responses


w2v_path = './app/models/word2vec.model'
category_path = './app/models/category.keras'

label = ['DIY자재/용품', '공구', '거실가구', '서재/사무용가구', '인테리어소품', '주방가구', '솜류',
       '수납가구', '수예', '해외여행', '침실가구', '아동/주니어가구', '아웃도어가구', '악기', '캠핑',
       '침구단품', '침구세트', '문구/사무용품', '카페트/러그', '커튼/블라인드', '휴대폰액세서리', '홈데코',
       '생활용품', '주방용품', '수납/정리용품', '패션소품', '골프', '오토바이/스쿠터', '학습기기', '종교',
       '교구', '수집품', '완구/매트', 'DVD', '인형', '위생/건강용품', '음반', '바디케어', '노트북',
       '생활편의', '욕실용품', '예체능레슨', '', '음료', '영상가전', '정원/원예용품', '임산부용품',
       '임부복', '신생아의류', '유아침구', '외출용품', '수유용품', '발건강용품', '재활운동용품', '안마용품',
       '건강측정용품', '반려동물', '자동차용품', '건강식품', '밀키트', '냉동/간편조리식품', '원데이클래스',
       '이미용가전', '다이어트식품', '마라톤용품', '요가/필라테스', '의료용품', '관상어용품', '클렌징',
       '가루/분말류', '낚시', '등산', '테니스', '스포츠액세서리', '배드민턴', '보호용품', '자전거',
       '농구', '축구', '카메라/캠코더용품', '소프트웨어', '원예/식물', '국내여행/체험', '자기계발/취미 레슨',
       '주방가전', '게임기/타이틀', '건강관리용품', '네트워크장비', 'PC액세서리', '구강위생용품', '세탁용품',
       '태블릿PC액세서리', '지갑', '양말', '남성가방', '남성신발', '남성의류', '색조메이크업', '음향가전',
       '화방용품', '주변기기', '모니터주변기기', '모니터', '벨트', '출산/돌기념품', '안전용품', '생활가전',
       '노트북액세서리', '멀티미디어장비', '여행용가방/소품', '주얼리', '계절가전', '모자', '여성의류',
       '유아동의류', '유아동잡화', '수영복/용품', '남성언더웨어/잠옷', '이유식용품', '유아동언더웨어/잠옷',
       '목욕용품', '라면/면류', '실버용품', '분유', '여성언더웨어/잠옷', '여성신발', '여성가방', 'PC부품',
       '과자/베이커리', 'PC', '블루레이', '선글라스/안경테', '스키/보드', '헤어액세서리', '검도',
       '스케이트/보드/롤러', '네일케어', '아기간식', '헬스', '농산물', '저장장치', '장류', '물티슈',
       '수산물', '광학기기/용품', '카시트', '유아가구', '베이스메이크업', '시계', '이유식', '축산물',
       '수영', '기저귀', '뷰티소품', '장갑', '청소용품', '자동차기기', '헤어스타일링', '스킨케어',
       '눈건강용품', '태블릿PC', '휴대폰', '댄스', '주류', '인라인스케이트', '향수', '헤어케어',
       '냉온/찜질용품', '당뇨관리용품', '물리치료/저주파용품', '구강청결용품', '남성화장품', '유모차', '조미료',
       '좌욕/좌훈용품', '무술용품', '수련용품', '야구', '권투', '기타스포츠용품', '당구용품', '배구',
       '볼링', '스쿼시', '탁구', '스킨스쿠버', '식용유/오일', '신발용품', '족구', '스킨/바디용품',
       '김치', '마스크/팩', '반찬', '소스/드레싱', '통조림/캔', '유가공품', '잼/시럽', '제과/제빵재료',
       '국내렌터카', '홈케어서비스', '소독/살균용품', '유아동 주얼리', '유아발육용품', '유아세제', '순금',
       '선케어']

w2v_model = Word2Vec.load(w2v_path)
model = tf.keras.models.load_model(category_path)

UNK_IDX = len(w2v_model.wv.index_to_key)
MAX_LEN = 32

