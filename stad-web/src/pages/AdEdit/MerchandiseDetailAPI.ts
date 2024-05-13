import axios from "axios";
import {useState} from "react";

export async function getMerchandiseDetail(productId: number) {
  try {
    const response = await axios.get(`/api/product/info`, {
      params: {
        productId: productId,
      },
    });
    console.log("상품 조회 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("상품 조회 실패", error);
    return null;
  }
}

export async function modifyMerchandiseDetail(data: any) {
  if (!data || data.length === 0) return;
  const request= {
    'productId' :data.productId,
    'name' : data.name,
    'imgs' : data.imgs,
    'thumbnail' : data.thumbnail,
    'cityDeliveryFee' : data.cityDeliveryFee,
    'mtDeliveryFee' : data.mtDeliveryFee,
    'expStart' : data.expStart + "T00:00:00",
    'expEnd' : data.expEnd + "T00:00:00",
    'productTypeList' : data.productTypeList,
  }
  console.log("######",data);
  console.log("######",data.productId);
  console.log(request);
  try {
    const response = await axios.put(`/api/product/update`,
      JSON.stringify(request),
        {headers: {
            'Content-Type': 'application/json' // JSON 데이터를 전송한다고 명시
          }}
    );
    console.log("상품 수정 성공", response.data);
    return response.data;
  } catch (error) {
    console.error("상품 수정 실패", error);
    return null;
  }
}
