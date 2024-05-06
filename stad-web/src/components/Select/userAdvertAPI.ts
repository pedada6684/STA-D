import axios from "axios";

// 유저 광고 리스트 조회
export async function getAdList(userId: number, accessToken: string | null) {
  try {
    const response = await axios.get(`/api/advert/get-list`, {
      params: {
        userId: userId,
      },
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("유저 광고 리스트 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("유저 광고 리스트 조회 실패", error);
  }
}
