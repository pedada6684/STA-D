import { useEffect, useRef, useState } from "react";
import WebNav from "../../components/Nav/WebNav";
import SelectAdListBox from "../../components/Select/SelectAdListBox";
import styles from "./AdManagement.module.css";
import AllExposure from "./All/AllExposure";
import AllClick from "./All/AllClick";
import SecondContent from "../../components/Container/SecondContent";
import AllOrder from "./All/AllOrder";
import AllRevenue from "./All/AllRevenue";
import Dots from "./Dots/Dots";
export default function AdManagement() {
  // selectAdList에서 갖고온 value를 props로 내보내기 위한 useState
  const [advertId, setAdvertId] = useState<number | null>(null);
  const [scrollIdx, setScrollIdx] = useState(1);
  const mainWrapperRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    const wheelHandler = (e: WheelEvent) => {
      e.preventDefault();
      const { deltaY } = e;
      const pageHeight = window.innerHeight;
      const newScrollIdx = scrollIdx + (deltaY > 0 ? 1 : -1);
      if (newScrollIdx >= 1 && newScrollIdx <= 4) {
        // 페이지 범위 제한
        mainWrapperRef.current?.scrollTo({
          top: pageHeight * (newScrollIdx - 1),
          left: 0,
          behavior: "smooth",
        });
        setScrollIdx(newScrollIdx);
      }
    };

    const refCurrent = mainWrapperRef.current;
    refCurrent?.addEventListener("wheel", wheelHandler);
    return () => {
      refCurrent?.removeEventListener("wheel", wheelHandler);
    };
  }, [scrollIdx]);
  return (
    <div className={`${styles.scrollSnapContainer}`} ref={mainWrapperRef}>
      <WebNav />
      <Dots scrollIdx={scrollIdx} />
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
