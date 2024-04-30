import axios from "axios";
import { EnterpriseData } from "./EnterprisesEdit";

export async function profileImgUpload(
  file: File,
  userId: number,
  accessToken: string | null
) {
  if (!file) return null;
  console.log(file);
  const formData = new FormData();
  formData.append("memberId", userId.toString());
  formData.append("profileImg", file);
  try {
    const res = await axios.post(`/api/user/profile`, formData, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("업로드 성공", res);
    return res.data;
  } catch (error) {
    console.error("업로드 실패", error);
  }
}

export async function postProfileEdit(
  userData: EnterpriseData,
  accessToken: string,
  file: File
) {
  const formData = new FormData();
  Object.keys(userData).forEach((key) => {
    const value = userData[key];
    if (value != null) {
      if (value !== null) {
        if (typeof value === "number") {
          // 숫자형 값을 문자열로 변환
          formData.append(key, value.toString());
        } else if (value instanceof File) {
          // 파일인 경우, 파일 그대로 추가
          formData.append(key, value);
        } else {
          // 그 외의 경우 (주로 문자열), 값을 그대로 추가
          formData.append(key, value);
        }
      }
    }
  });
  console.log(formData);
  try {
    const response = await axios.post("/api/user/update", formData, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("Update Success:", response.data);
  } catch (error) {
    console.error("Update Error:", error);
    throw error;
  }
}
export async function GetEnterpriseInfo(userId: number, token: string) {
  const response = await axios.get(`/api/user`, {
    params: {
      userId: userId,
    },
    headers: { Authorization: `Bearer ${token}` },
  });
  return await response.data;
}
