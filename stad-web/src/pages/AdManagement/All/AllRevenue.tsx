import BarChart from "../../../components/AdManagement/BarChart";
import Frame from "../../../components/AdManagement/Frame";
import GetRevenue from "../../../components/AdManagement/GetRevenue";
import PieChart from "../../../components/AdManagement/PieChart";
import styles from "../AdManagement.module.css";
import { AdvertIdProps } from "./AllExposure";
export default function AllRevenue({ advertId }: AdvertIdProps) {
  return (
    <div className={`${styles.etcContainer}`}>
      <GetRevenue advertId={advertId} />
      <Frame advertId={advertId} />
      <div className={`${styles.chartWrapper}`}>
        <PieChart title="30일 동안 수익 비율" dataType="totalRevenue" />
        <BarChart title="30일 동안 수익" dataType="totalRevenue" />
      </div>
    </div>
  );
}
