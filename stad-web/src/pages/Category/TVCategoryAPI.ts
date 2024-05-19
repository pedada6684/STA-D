import axios from "axios";

export async function GetSeriesVideoList(accessToken: string | null) {
  try {
    const response = await axios.get(`/api/contents-concept/series`, {});
    console.log("시리즈 각 카테고리 메인 영상 목록 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("시리즈 각 카테고리 메인 영상 목록 조회 실패", error);
  }
}

export async function GetMovieVideoList(accessToken: string | null) {
  try {
    const response = await axios.get(`/api/contents-concept/movie`, {});
    console.log("영화 각 카테고리 메인 영상 목록 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("영화 각 카테고리 메인 영상 목록 조회 실패", error);
  }
}
