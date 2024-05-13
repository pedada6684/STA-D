import { useEffect, useState } from "react";
import styles from "./EnrolledList.module.css";
import dummyData from "./dummyAdData.json";
import {
  deleteAdvert,
  getAdvertList,
  getContentConcept,
  modifyAdvert,
} from "../../AdEnroll/AdEnrollApi";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import { useNavigate } from "react-router";
import { useQuery, useQueryClient } from "react-query";

interface adType {
  advertId?: number;
  title?: string;
  description?: string;
  startDate?: string;
  endDate?: string;
  advertType?: string;
  advertCategory?: string;
  directVideoUrl?: string;
  bannerImgUrl?: string;
  selectedContentList?: number[];
  advertVideoUrlList?: string[];
}

export default function EnrolledAdList() {
  const [adsList, setAdsList] = useState<adType[]>([]); // 광고 목록
  const userId = useSelector((state: RootState) => state.user.userId);
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { data: list, isLoading } = useQuery(
    ["getAds", userId],
    () => getAdvertList(userId),
    {
      onSuccess: (list) => {
        setAdsList(list);
      },
    }
  );

  useEffect(() => {
    queryClient.invalidateQueries({ queryKey: "getAds" });
  }, [adsList.length, queryClient]);

  const editClick = (advert: adType) => {
    console.log(`Edit clicked for ad with ID:`, advert.advertId);
    navigate(`/ads/${advert.advertId}`);
    // modifyAdvert(advert);
  };

  const deleteClick = async (advertId: number | undefined) => {
    console.log(`Delete clicked for ad with ID:` + advertId);
    try {
      await deleteAdvert(advertId); // 광고 삭제 API 호출
      setAdsList((currentAds) =>
        currentAds.filter((ad) => ad.advertId !== advertId)
      );
      // 삭제 성공 후 광고 목록에서 해당 광고 제거
    } catch (error) {
      console.error("Failed to delete ad:", error);
    }
  };

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.title}`}>등록 광고 목록</div>
      <div className={`${styles.gridContainer}`}>
        <div className={`${styles.gridCategory}`}>이미지</div>
        <div className={`${styles.gridCategory}`}>광고명</div>
        <div className={`${styles.gridCategory}`}>광고기간</div>
        <div className={`${styles.gridCategory} `}>수정/삭제</div>
        {adsList.map((ad) => (
          <>
            <div className={`${styles.gridItem} ${styles.gridImg}`}>
              <img src={ad.bannerImgUrl} alt="Ad" />
            </div>
            <div className={`${styles.gridItem}`}>{ad.title}</div>
            <div className={`${styles.gridItem}`}>
              {ad.startDate} ~ {ad.endDate}
            </div>
            <div className={`${styles.gridItem}`}>
              <span
                style={{ cursor: "pointer", marginRight: "10px" }}
                onClick={() => editClick(ad)}
              >
                수정
              </span>
              <span>/</span>
              <span
                style={{ cursor: "pointer", marginLeft: "10px" }}
                onClick={() => deleteClick(ad.advertId)}
              >
                삭제
              </span>
            </div>
          </>
        ))}
      </div>
    </div>
  );
}
