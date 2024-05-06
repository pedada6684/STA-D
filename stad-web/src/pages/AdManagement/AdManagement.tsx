import { useState } from "react";
import WebNav from "../../components/Nav/WebNav";
import SelectAdListBox from "../../components/Select/SelectAdListBox";
import styles from "./AdManagement.module.css";
import AllExposure from "./All/AllExposure";
import AllClick from "./All/AllClick";
import SecondContent from "../../components/Container/SecondContent";
import AllOrder from "./All/AllOrder";
import AllRevenue from "./All/AllRevenue";
export default function AdManagement() {
  // selectAdList에서 갖고온 value를 props로 내보내기 위한 useState
  const [advertId, setAdvertId] = useState<number | null>(null);
  return (
    <div className={`${styles.scrollSnapContainer}`}>
      <WebNav />
      <SecondContent>
        <div className={`${styles.firstWrapper}`}>
          <div className={`${styles.main}`}>
            <div className={`${styles.title}`}>대시보드</div>
            <SelectAdListBox onAdSelect={setAdvertId} />
          </div>
        </div>
        <AllExposure advertId={advertId} />
      </SecondContent>
      <SecondContent>
        <AllClick advertId={advertId} />
      </SecondContent>
      <SecondContent>
        <AllOrder advertId={advertId} />
      </SecondContent>
      <SecondContent>
        <AllRevenue advertId={advertId} />
      </SecondContent>
    </div>
  );
}
