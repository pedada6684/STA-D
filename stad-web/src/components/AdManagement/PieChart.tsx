import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useEffect, useState } from "react";
import { getAdList } from "../Select/userAdvertAPI";
import { getTotal } from "./DashboardAPI";
import { adList } from "../Select/SelectAdListBox";
import ReactApexChart from "react-apexcharts";
import styles from "./ChartData.module.css";
export interface TotalResponse {
  totalAdvertVideo: number;
  totalAdvertClick: number;
  totalOrder: number;
  totalOrderCancel: number;
  totalRevenue: number;
}
export interface PieChartProps {
  title: string;
  dataType: keyof TotalResponse;
}

export default function PieChart({ title, dataType }: PieChartProps) {
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const userId = useSelector((state: RootState) => state.user.userId);
  // 차트에 넣을 데이터 상태관ㄹㅣ
  // 값 없어서 일단 더미로 UI 만듦
  const [series, setSeries] = useState<number[]>([50, 30, 20, 15]); // 더미 데이터
  const [labels, setLabels] = useState<string[]>([
    "Advert A",
    "Advert B",
    "Advert C",
    "Advert D",
  ]); // 더미 데이터
  useEffect(() => {
    // async function fetchData() {
    //   try {
    //     const adListData = await getAdList(userId, accessToken); // 유저 광고 리스트 조회
    //     const advertIds = adListData.data.map((ad: adList) => ad.advertId); // map 사용해서 advertId 담아주기
    //     const promise = advertIds.map((id: number) =>
    //       getTotal(id, accessToken)
    //     );
    //     const results = await Promise.all(promise); // 데이터 병렬을 위해 동기
    //     //데이터 pie chart 형식에 맞게 변환
    //     setSeries(results.map((res) => res[dataType]));
    //     setLabels(adListData.data.map((ad: adList) => ad.title));
    //   } catch (error) {
    //     console.error("데이터 로드 실패", error);
    //   }
    // }
    // fetchData();
  }, [accessToken, dataType]);
  const chartOptions: ApexCharts.ApexOptions = {
    chart: { type: "donut", height: 300 },
    labels,
    colors: ["#FEB019", "#FFDA00", "#F9ECAF", , "#FFF9DF"],
    dataLabels: {
      enabled: false,
    },
    stroke: {
      width: 0, // 테두리 제거
    },
    plotOptions: {
      pie: {
        donut: {
          size: "40%", // 내부 반경 크기 조절
          labels: {
            show: true,

            name: {
              show: true,
            },
            value: {
              show: true,
            },
          },
        },
      },
    },
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: { width: 200 },
          legend: {
            show: true,
            position: "left", // Set legend to the left side
            horizontalAlign: "center", // Center horizontally in the space it occupies
            verticalAlign: "middle", // Center vertically
            offsetX: 0,
            offsetY: 0,
          },
        },
      },
    ],
  };

  return (
    <div className={`${styles.container} ${styles.pie}`}>
      <div className={`${styles.title}`}>{title}</div>
      <div className={`${styles.pieContainer}`}>
        <ReactApexChart
          options={chartOptions}
          series={series}
          type="donut"
          width={350}
        />
      </div>
    </div>
  );
}
