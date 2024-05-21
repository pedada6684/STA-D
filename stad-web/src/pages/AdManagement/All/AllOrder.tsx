import { AdvertIdProps } from "./AllExposure";
import styles from "../AdManagement.module.css";
import GetOrder from "../../../components/AdManagement/GetOrder";
import PieChart from "../../../components/AdManagement/PieChart";
import BarChart from "../../../components/AdManagement/BarChart";
import Frame from "../../../components/AdManagement/Frame";
import { useEffect, useState } from "react";
export default function AllOrder({ advertId }: AdvertIdProps) {
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
          <GetOrder advertId={advertId} />
        </div>
        <div>
          <Frame advertId={advertId} />
        </div>
        <div>
          <BarChart title="30일 동안 주문 건수" dataType="totalOrder" />
        </div>
        <div>
          <PieChart title="30일 동안 주문 비율" dataType="totalOrder" />
        </div>
      </div>
    </div>
  );
}
