import axios from "axios";

export async function GetMainCarousel(accessToken: string | null) {
  try {
    const response = await axios.get(
      `/api/contents-detail/collections/popular`,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
      }
    );
    console.log("인기 영상 목록 조회 성공", response.data.detailList);
    return response.data.detailList;
  } catch (error) {
    console.error("인기 영상 목록 조회 실패", error);
  }
}

export async function GetRecentWatching(accessToken: string | null) {
  try {
    if (accessToken) {
      const response = await axios.get(
        `/api/contents-detail/collections/watching`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("시청 중인 영상 조회 성공", response.data);
      return response.data;
    }
  } catch (error) {
    console.error("시청 중인 영상 목록 조회 실패", error);
  }
}
