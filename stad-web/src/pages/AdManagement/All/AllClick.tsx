import GetClick from "../../../components/AdManagement/GetClick";
import { AdvertIdProps } from "./AllExposure";
import styles from "../AdManagement.module.css";
import PieChart from "../../../components/AdManagement/PieChart";
import BarChart from "../../../components/AdManagement/BarChart";
import Frame from "../../../components/AdManagement/Frame";
export default function AllClick({ advertId }: AdvertIdProps) {
  return (
    <div className={`${styles.etcContainer}`}>
      <div className={`${styles.chartWrapper}`}>
        <div>
          <GetClick advertId={advertId} />
        </div>
        <div>
          <Frame advertId={advertId} />
        </div>
        <div>
          <BarChart
            title="30일 동안 광고 클릭 건수"
            dataType="totalAdvertClick"
          />
        </div>
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
