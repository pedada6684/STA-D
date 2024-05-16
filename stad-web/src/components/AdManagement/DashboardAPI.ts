import axios from "axios";
export async function getViewCount(
  advertId: number | null,
  accessToken: string | null
) {
  try {
    const response = await axios.get(`/stats/log/daily/advert-video`, {
      params: {
        advertId: advertId,
      },
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("광고 시청 수 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("광고 시청 수 조회 실패", error);
  }
}

export async function getClickCount(
  advertId: number | null,
  accessToken: string | null
) {
  try {
    const response = await axios.get(`/stats/log/daily/click`, {
      params: {
        advertId: advertId,
      },
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("광고클릭 수 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("광고클릭 수 조회 실패", error);
  }
}

export async function getOrderCount(
  advertId: number | null,
  accessToken: string | null
) {
  try {
    const response = await axios.get(`/stats/log/daily/order`, {
      params: {
        advertId: advertId,
      },
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("광고주문 수 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("광고주문 수 조회 실패", error);
  }
}

export async function getRevenue(
  advertId: number | null,
  accessToken: string | null
) {
  try {
    const response = await axios.get(`/stats/log/daily/revenue`, {
      params: {
        advertId: advertId,
      },
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("광고 수익 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("광고 수익 조회 실패", error);
  }
}

export async function getTotal(
  advertId: number | null,
  accessToken: string | null
) {
  try {
    const response = await axios.get(`/stats/log/total`, {
      params: {
        advertId: advertId,
      },
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("30일 데이터 총합 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("30일 데이터 총합 조회 실패", error);
  }
}
