import axios from "axios";
import {useState} from "react";

export async function getMerchandiseDetail(productId: number) {
  try {
    const response = await axios.get(`/api/product/info`, {
      params: {
        id: productId,
      },
    });
    console.log("상품 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("상품 조회 실패", error);
    return null;
  }
}
