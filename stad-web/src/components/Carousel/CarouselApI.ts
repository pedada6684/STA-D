import axios from "axios";

export async function GetMainCarousel() {
  try {
    const response = await axios.get(`/api/contents-concept/updated`);
    console.log("최신 영상 목록 조회 성공", response.data.detailList);
    return response.data.detailList;
  } catch (error) {
    console.error("최신 영상 목록 조회 실패", error);
  }
}

export async function GetRecentWatching(userId: number) {
  try {
    const response = await axios.get(`/api/contents-detail/collections/watching`, {
      params: {
        userId: userId,
      },
    });
    console.log("시청 중인 영상 조회 성공", response.data);
    return response.data.detailList;
  } catch (error) {
    console.error("시청 중인 영상 목록 조회 실패", error);
  }
}

export async function GetSaveWatching(tvUserId: number) {
  try {
    const response = await axios.get(`/api/contents-detail/collections/bookmarked`, {
      params: {
        userId: tvUserId,
      },
    });
    console.log("찜한 영상 목록 조회", response.data);
    return response.data.detailList;
  } catch (error) {
    console.error("찜한 영상 목록 조회 실패", error);
  }
}
