import { useEffect, useState } from "react";
import BarChart from "../../../components/AdManagement/BarChart";
import Frame from "../../../components/AdManagement/Frame";
import GetExposure from "../../../components/AdManagement/GetExposure";
import PieChart from "../../../components/AdManagement/PieChart";
import styles from "../AdManagement.module.css";
export interface AdvertIdProps {
  advertId: number | null;
}
export default function AllExposure({ advertId }: AdvertIdProps) {
  useEffect(() => {
    const timer = setTimeout(() => {
      // 차트 로드 순서 제어
    }, 20000); // 300ms 지연

    return () => clearTimeout(timer);
  }, []);
  return (
    <div>
      <div className={`${styles.chartWrapper}`}>
        <div>
          <GetExposure advertId={advertId} />
        </div>
        <div>
          <Frame advertId={advertId} />
        </div>
        <div>
          <BarChart
            title="30일 동안 광고 시청 건수"
            dataType="totalAdvertVideo"
          />
        </div>
        <div>
          <PieChart
            title="30일 동안 광고 시청 비율"
            dataType="totalAdvertVideo"
          />
        </div>
      </div>
    </div>
  );
}
