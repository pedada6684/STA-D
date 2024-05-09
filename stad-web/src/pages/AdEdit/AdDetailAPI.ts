import axios from "axios";

export async function getAdvertDetail(advertId: number) {
  try {
    const response = await axios.get(`/api/advert/get`, {
      params: {
        advertId: advertId,
      },
    });
    console.log("상세 조회 성공", response);
    return response.data;
  } catch (error) {
    console.error("상세 조회 실패", error);
  }
}
