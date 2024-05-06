import GetClick from "../../../components/AdManagement/GetClick";
import { AdvertIdProps } from "./AllExposure";
import styles from "../AdManagement.module.css";
import PieChart from "../../../components/AdManagement/PieChart";
import BarChart from "../../../components/AdManagement/BarChart";
import Frame from "../../../components/AdManagement/Frame";
export default function AllClick({ advertId }: AdvertIdProps) {
  return (
    <div className={`${styles.etcContainer}`}>
      <GetClick advertId={advertId} />
      <Frame advertId={advertId} />
      <div className={`${styles.chartWrapper}`}>
        <PieChart
          title="30일 동안 광고 클릭 비율"
          dataType="totalAdvertClick"
        />
        <BarChart
          title="30일 동안 광고 클릭 건수"
          dataType="totalAdvertClick"
        />
      </div>
    </div>
  );
}
