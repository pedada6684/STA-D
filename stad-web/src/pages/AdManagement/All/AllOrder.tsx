import { AdvertIdProps } from "./AllExposure";
import styles from "../AdManagement.module.css";
import GetOrder from "../../../components/AdManagement/GetOrder";
import PieChart from "../../../components/AdManagement/PieChart";
import BarChart from "../../../components/AdManagement/BarChart";
import Frame from "../../../components/AdManagement/Frame";
export default function AllOrder({ advertId }: AdvertIdProps) {
  return (
    <div className={`${styles.etcContainer}`}>
      <GetOrder advertId={advertId} />
      <Frame advertId={advertId} />
      <div className={`${styles.chartWrapper}`}>
        <PieChart title="30일 동안 주문 비율" dataType="totalOrder" />
        <BarChart title="30일 동안 주문 건수" dataType="totalOrder" />
      </div>
    </div>
  );
}
