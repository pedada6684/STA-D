import axios from "axios";
import { dataProps } from "../../components/Button/EditButton";

export async function getAdvertDetail(advertId: number) {
  try {
    const response = await axios.get(`/api/advert/get`, {
      params: {
        advertIds: advertId,
      },
    });
    console.log("상세 조회 성공", response.data.data[0]);
    return response.data.data[0];
  } catch (error) {
    console.error("상세 조회 실패", error);
  }
}

export async function deleteAdvertVideo(advertVideoId: number) {
  try {
    const response = await axios.delete(`/api/advert-video/delete-video`, {
      params: {
        advertVideoId: advertVideoId,
      },
    });
    console.log("광고 영상 삭제", response.data);
    return response.data;
  } catch (error) {
    console.log("광고 영상 삭제 실패", error);
  }
}

export const modifyAdvert = async (data: any) => {
  if (!data || data.length == 0) return;
  const request = {
    advertId: data.formData.advertId,
    title: data.formData.title,
    description: "",
    startDate: data.formData.startDate + "T00:00:00",
    endDate: data.formData.endDate + "T00:00:00",
    advertType: data.formData.advertType,
    advertCategory: data.formData.advertCategory,
    directVideoUrl: data.formData.directVideoUrl,
    bannerImgUrl: data.formData.bannerImgUrl,
    selectedContentList: data.formData.selectedContentList,
    advertVideoUrlList: data.formData.advertVideoUrlList,
  };
  console.log(request);
  try {
    const response = await fetch(`/api/advert`, {
      method: "PUT",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json", // JSON 데이터를 전송한다고 명시
      },
    });
    const data = await response.json(); // JSON 형태로 응답 받음
    console.log("광고 영상 수정 성공", data);
    return data;
  } catch (error) {
    console.error("광고 수정 실패 : ", error);
  }
};
