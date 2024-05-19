import { useEffect, useState } from "react";
import BarChart from "../../../components/AdManagement/BarChart";
import Frame from "../../../components/AdManagement/Frame";
import GetRevenue from "../../../components/AdManagement/GetRevenue";
import PieChart from "../../../components/AdManagement/PieChart";
import styles from "../AdManagement.module.css";
import { AdvertIdProps } from "./AllExposure";
export default function AllRevenue({ advertId }: AdvertIdProps) {
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
          <GetRevenue advertId={advertId} />
        </div>
        <div>
          <Frame advertId={advertId} />
        </div>
        <div>
          <BarChart title="30일 동안 수익" dataType="totalRevenue" />
        </div>
        <div>
          <PieChart title="30일 동안 수익 비율" dataType="totalRevenue" />
        </div>
      </div>
    </div>
  );
}
