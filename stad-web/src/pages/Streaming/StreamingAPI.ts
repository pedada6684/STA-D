import axios from "axios";

export async function getVideoConcept(
  accessToken: string | null,
  detailId: number
) {
  try {
    if (accessToken) {
      const response = await axios.get(`/api/contents-detail/${detailId}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
      });
      console.log("영상 상세 정보 조회 성공", response.data);
      return response.data;
    }
  } catch (error) {
    console.error("영상 상세 정보 조회 실패", error);
  }
}

export async function getSeriesVideoList(
  accessToken: string | null,
  category: string
) {
  try {
    if (accessToken) {
      const response = await axios.get(
        `/api/contents-concept/series/${category}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("(시리즈)카테고리 영상 목록 조회 성공", response.data);
      return response.data.responseList;
    }
  } catch (error) {
    console.error("(시리즈)카테고리 영상 목록 조회 실패", error);
  }
}

export async function getMovieVideoList(
  accessToken: string | null,
  category: string
) {
  try {
    if (accessToken) {
      const response = await axios.get(
        `/api/contents-concept/movie/${category}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("(영화)카테고리 영상 목록 조회 성공", response.data);
      return response.data.responseList;
    }
  } catch (error) {
    console.error("(영화)카테고리 영상 목록 조회 성공", error);
  }
}

export async function getStreaming(
  accessToken: string | null,
  detailId: number
) {
  try {
    if (accessToken) {
      const response = await axios.get(
        `/api/contents-detail/streaming/${detailId}`
      );
      console.log("스트리밍 성공", response.data);
      return response.data;
    }
  } catch (error) {
    console.error("스트리밍 실패", error);
  }
}

export async function getVideoDetail(
  accessToken: string | null,
  conceptId: number
) {
  // conceptId로 detail 조회
  try {
    if (accessToken) {
      console.log(conceptId);
      const response = await axios.get(`/api/contents-detail/collections`, {
        params: {
          conceptId: conceptId,
        },
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
      });
      console.log("디테일 조회 성공", response.data);
      return response.data;
    }
  } catch (error) {
    console.error("디테일 조회 실패", error);
  }
}
// 시청 영상 등록
export async function postWatchAdd(
  accessToken: string | null,
  userId: number,
  detailId: number
) {
  try {
    if (accessToken) {
      console.log("detailId", detailId, "/userId", userId);
      const response = await axios.post(
        `/api/contents-watch/add`,
        {
          userId: userId,
          detailId: detailId,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("시청 영상 생성 성공", response.data);
      return response.data;
    }
  } catch (error) {
    console.error("시청 영상 생성 실패", error);
  }
}
