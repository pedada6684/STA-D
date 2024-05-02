import axios from "axios";

export async function getSeriesCategory(accessToken: string | null) {
  try {
    if (accessToken) {
      const response = await axios.get(
        `/api/contents-category/collection/series`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("시리즈 카테고리 조회 성공", response.data);
      return response.data.categoryList;
    }
  } catch (error) {
    console.error("시리즈 카테고리 조회 실패", error);
  }
}

export async function getMovieCategory(accessToken: string | null) {
  try {
    if (accessToken) {
      const response = await axios.get(
        `/api/contents-category/collection/movie`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("영화 카테고리 조회 성공", response.data);
      return response.data.categoryList;
    }
  } catch (error) {
    console.error("영화 카테고리 조회 실패", error);
  }
}
