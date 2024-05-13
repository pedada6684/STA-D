import axios from "axios";

export async function getVideoConcept(accessToken: string | null, detailId: number) {
  try {
    const response = await axios.get(`/api/contents-detail/${detailId}`, {});
    console.log("영상 상세 정보 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("영상 상세 정보 조회 실패", error);
  }
}

export async function getSeriesVideoList(accessToken: string | null, category: string) {
  try {
    const response = await axios.get(`/api/contents-concept/series/${category}`);
    console.log("(시리즈)카테고리 영상 목록 조회 성공", response.data);
    return response.data.responseList;
  } catch (error) {
    console.error("(시리즈)카테고리 영상 목록 조회 실패", error);
  }
}

export async function getMovieVideoList(accessToken: string | null, category: string) {
  try {
    const response = await axios.get(`/api/contents-concept/movie/${category}`);
    console.log("(영화)카테고리 영상 목록 조회 성공", response.data);
    return response.data.responseList;
  } catch (error) {
    console.error("(영화)카테고리 영상 목록 조회 성공", error);
  }
}

export async function getStreaming(accessToken: string | null, detailId: number) {
  try {
    const response = await axios.get(`/api/contents-detail/streaming/${detailId}`);
    console.log("스트리밍 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("스트리밍 실패", error);
  }
}

// 시청 영상 등록
export async function postWatchAdd(accessToken: string | null, userId: number, detailId: number) {
  try {
    console.log("detailId", detailId, "/userId", userId);
    const response = await axios.post(`/api/contents-watch/add`, {
      userId: userId,
      detailId: detailId,
    });
    console.log("시청 영상 생성 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("시청 영상 생성 실패", error);
  }
}

// 광고 url 리스트 조회
export async function getAdvertUrlList(accessToken: string | null, userId: number, detailId: number) {
  try {
    console.log("detailId", detailId, "/userId", userId);
    const response = await axios.get(`/api/advert-video/get-video-list/${detailId}`, {
      params: {
        userId: userId,
      },
    });
    console.log("광고 url 리스트 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("광고 url 리스트 조회 실패", error);
  }
}

// 북마크 검사
export async function getIsBookmarked(accessToken: string | null, userId: number, conceptId: number) {
  try {
    console.log("userId", userId, "/conceptId", conceptId);
    const response = await axios.get(`/api/contents-bookmark/check/${conceptId}`, {
      params: {
        userId: userId,
      },
    });
    console.log("북마크 정보 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("북마크 정보 조회 실패", error);
  }
}

// 북마크 등록
export async function postBookmarkAdd(accessToken: string | null, userId: number, conceptId: number) {
  try {
    console.log("conceptId", conceptId, "/userId", userId);
    const response = await axios.post(`/api/contents-bookmark/add`, {
      userId: userId,
      conceptId: conceptId,
    });
    console.log("북마크 생성 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("북마크 생성 실패", error);
  }
}

// 북마크 삭제
export async function deleteBookmark(accessToken: string | null, userId: number, conceptId: number) {
  try {
    console.log("conceptId", conceptId, "/userId", userId);
    const response = await axios.post(`/api/contents-bookmark/delete`, {
      userId: userId,
      conceptId: conceptId,
    });
    console.log("북마크 삭제 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("북마크 삭제 실패", error);
  }
}
