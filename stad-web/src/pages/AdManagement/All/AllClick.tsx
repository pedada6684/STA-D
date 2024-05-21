import GetClick from "../../../components/AdManagement/GetClick";
import { AdvertIdProps } from "./AllExposure";
import styles from "../AdManagement.module.css";
import PieChart from "../../../components/AdManagement/PieChart";
import BarChart from "../../../components/AdManagement/BarChart";
import Frame from "../../../components/AdManagement/Frame";
import { useEffect, useState } from "react";
export default function AllClick({ advertId }: AdvertIdProps) {
  useEffect(() => {
    const timer = setTimeout(() => {
      // 차트 로드 순서 제어
    }, 20000); // 300ms 지연

    return () => clearTimeout(timer);
  }, []);
  return (
    <div className={`${styles.etcContainer}`}>
      <div className={`${styles.chartWrapper}`}>
        <div>
          <GetClick advertId={advertId} />
        </div>
        <div>
          <Frame advertId={advertId} />
        </div>
        <BarChart
          title="30일 동안 광고 클릭 건수"
          dataType="totalAdvertClick"
        />
        <div>
          <PieChart
            title="30일 동안 광고 클릭 비율"
            dataType="totalAdvertClick"
          />
        </div>
      </div>
    </div>
  );
}
