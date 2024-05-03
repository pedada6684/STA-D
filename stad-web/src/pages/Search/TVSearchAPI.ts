import axios from "axios";

export async function GetSearch(value: string, accessToken: string | null) {
  try {
    const response = await axios.get(
      `/api/contents-concept/search/${encodeURIComponent(value)}`
    );
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("검색 실패", error);
  }
}
