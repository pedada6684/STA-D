from app.common.preprocess import preprocess
from app.dto.recommend_dto import request_recommendInfo

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity


# def get_category(requests: list[request_category]):
#     responses = []
#     for request in requests:
#         text = preprocess(request.video_transcript)
#         result = predict(sentence_to_sequence(text))
#         response = response_category(video_id=request.video_id, category=result)
#         responses.append(response)
#
#     return responses
# docs = [
#     '먹고 싶은 사과',  # 문서0
#     '먹고 싶은 바나나',  # 문서1
#     '길고 노란 바나나 바나나',  # 문서2
#     '저는 과일이 좋아요'  # 문서3
# ]
# vect = CountVectorizer()  # Counter Vectorizer 객체 생성
# # 문장을 Counter Vectorizer 형태로 변형
# countvect = vect.fit_transform(docs)
# print(countvect)  # 4x9 : 4개의 문서에 9개의 단어
#
# text = preprocess('먹고 싶은 사과 먹고 싶은 바나나 길고 노란 바나나 바나나 저는 과일이 좋아요')
# print(text)
#
# from numpy import dot
# from numpy.linalg import norm
#
#
# def cos_sim(A, B):
#     return dot(A, B) / (norm(A) * norm(B))
#
#
# li = ['스타트업기업의 창업성공률 제고를 위한 창업금융의 효율적 운영 방안 연구',
#       '4차 산업혁명시대에 민관의 청년창업활성화를 위한 효율적인 협조금융체계의 개편방안 연구']
#
# tfidf_vect_simple = TfidfVectorizer()
# feature_vect_simple = tfidf_vect_simple.fit_transform(li)
#
# # Dense Matrix 형태로 변환.
# feature_vect_dense = feature_vect_simple.todense()
#
# # 리스트 내 첫번째 문장과 두번째 문장의 feature vector 추출
# vect1 = np.array(feature_vect_dense[0]).reshape(-1, )
# vect2 = np.array(feature_vect_dense[1]).reshape(-1, )
#
# # Cosine 유사도 추출
# similarity_simple = cos_sim(vect1, vect2)
# print('문장 1과 문장 2의 코사인 유사도 : {0:.3f}'.format(similarity_simple))
#


#
# corpus = [doc1, doc2, doc3]

# X = vectorized.fit_transform(corpus).todense()
#
# print("[쇼생크 탈출] & [대부] : ", cosine_similarity(X[0], X[1]))
# print("[쇼생크 탈출] & [인셉션] : ", cosine_similarity(X[0], X[2]))
# print("[대부] & [인셉션] : ", cosine_similarity(X[1], X[2]))


def cos_sim(requests: request_recommendInfo):
    similar_docs = []

    origin = preprocess(requests.concern)
    similar_docs.append(origin)
    
    for i, idx in enumerate(requests.videoDtoList):
        similar_docs.append(preprocess(idx.video_transcript))

    result = []
    similar_text = TfidfVectorizer().fit_transform(similar_docs)

    for i in range(1, len(similar_docs) - 1):
        origin = similar_text[0:1]
        compare = similar_text[i:i + 1]
        
        result.append((requests.videoDtoList[i].video_id, cosine_similarity(origin, compare)))
        
        result.sort(key=lambda x: x[1], reverse=True)

    return result[:15]