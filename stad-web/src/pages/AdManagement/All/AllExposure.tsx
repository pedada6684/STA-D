import BarChart from "../../../components/AdManagement/BarChart";
import Frame from "../../../components/AdManagement/Frame";
import GetExposure from "../../../components/AdManagement/GetExposure";
import PieChart from "../../../components/AdManagement/PieChart";
import styles from "../AdManagement.module.css";
export interface AdvertIdProps {
  advertId: number | null;
}
export default function AllExposure({ advertId }: AdvertIdProps) {
  return (
    <div>
      <GetExposure advertId={advertId} />
      <Frame advertId={advertId} />
      <div className={`${styles.chartWrapper}`}>
        <PieChart
          title="30일 동안 광고 시청 비율"
          dataType="totalAdvertVideo"
        />
        <BarChart
          title="30일 동안 광고 시청 건수"
          dataType="totalAdvertVideo"
        />
      </div>
    </div>
  );
}
