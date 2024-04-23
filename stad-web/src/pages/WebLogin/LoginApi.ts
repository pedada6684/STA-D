import { useSelector } from "react-redux";
import { jwtDecode } from "jwt-decode";
import axios from "axios";
import { useState } from "react";

export async function GetUser(accessToken: string | null) {
  if (accessToken) {
    // accessToken 벗겨서 userId 얻기
    try {
      console.log(accessToken);
      const data = jwtDecode(accessToken);
      console.log(data);
      if (data.sub === undefined) {
        console.log("User ID is undefined");
        return; // 함수에서 나가기
      }
      console.log(data.sub);
      const userId = parseInt(data.sub);
      const response = await axios.get(`/api/user`, {
        params: {
          userId: userId,
        },
        headers: { Authorization: `Bearer ${accessToken}` },
      });
      console.log(response);
      return response.data;
    } catch (error) {
      console.log("회원정보 조회 실패", error);
    }
  }
}

export async function GetLogout(accessToken: string | null) {
  if (accessToken) {
    // accessToken 벗겨서 userId 얻기
    try {
      console.log(accessToken);
      const data = jwtDecode(accessToken);
      console.log(data);
      const userId = data.sub;
      const response = await axios.get(`/api/v1/auth/logout`, {
        params: {
          userId: userId,
        },
        headers: { Authorization: `Bearer ${accessToken}` },
      });
      return response.data;
    } catch (error) {
      console.log("로그아웃 실패", error);
    }
  }
}
