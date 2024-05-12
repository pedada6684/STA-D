import axios from "axios";
import { dataProps } from "../../components/Button/EditButton";

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
  console.log(data);
  const request = {
    advertId: data.advertId,
    title: data.title,
    description: "",
    startDate: data.startDate + "T00:00:00",
    endDate: data.endDate + "T00:00:00",
    advertType: data.advertType,
    advertCategory: data.advertCategory,
    directVideoUrl: data.directVideoUrl,
    bannerImgUrl: data.bannerImgUrl,
    selectedContentList: data.selectedContentList,
    advertVideoUrlList: data.advertVideoUrlList,
  };
  console.log(request.advertVideoUrlList);
  try {
    const response = await fetch(`/api/advert`, {
      method: "PUT",
      body: JSON.stringify(request),
    });
    if (!response.ok) {
      throw new Error("광고 수정 실패");
    }
    const data = await response.json(); // JSON 형태로 응답 받음
    const result = data.result;
    console.log(result);
    return result.data;
  } catch (error) {
    console.error("광고 수정 실패 : ", error);
    return null;
  }
};

export const modifyVideo = async (videoId: number, video: string) => {
  try {
    const response = axios.post(`/api/advert-video/modify-video`, {
      videoId: videoId,
      video: video,
    });
    console.log("광고 영상 수정", response);
  } catch (error) {
    console.error("광고 영상 수정 실패", error);
  }
};
