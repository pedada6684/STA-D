import { useEffect, useState } from "react";
import styles from "./EnrolledList.module.css";
import dummyData from "./dummyAdData.json";

interface adType {
  title: string;
  description: string;
  startDate: string;
  endDate: string;
  category: string;
  fullVideoUrl?: string;
  directVideoUrl?: string;
  directImgUrl: string;
  selectedContentList: string[];
}

export default function EnrolledAdList() {
  const [adsList, setAdsList] = useState<adType[]>([]); // 광고 목록
  useEffect(() => {}, []);

  useEffect(() =>{},[]);

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.title}`}>등록 광고 목록</div>
      <div className={`${styles.gridContainer}`}>
        <div className={`${styles.gridCategory}`}>이미지</div>
        <div className={`${styles.gridCategory}`}>광고명</div>
        <div className={`${styles.gridCategory}`}>광고기간</div>
        <div className={`${styles.gridCategory} `}>수정/삭제</div>
        {dummyData.map((ad) => (
          <>
            <div className={`${styles.gridItem} ${styles.gridImg}`}>
              <img src={ad.directImgUrl} alt="Ad" />
            </div>
            <div className={`${styles.gridItem}`}>{ad.title}</div>
            <div className={`${styles.gridItem}`}>
              {ad.startDate} ~ {ad.endDate}
            </div>
            <div className={`${styles.gridItem}`}>수정 / 삭제</div>
          </>
        ))}
      </div>
    </div>
  );
}
