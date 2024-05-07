import { ApexOptions } from "apexcharts";
import { useEffect, useState } from "react";
import ReactApexChart from "react-apexcharts";
import styles from "./ChartData.module.css";
import { AdvertIdProps } from "../../pages/AdManagement/All/AllExposure";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { getViewCount } from "./DashboardAPI";
interface SeriesData {
  name: string;
  data: { x: string; y: number }[];
}
// // 날짜와 수익 데이터를 생성하는 함수
// function generateDummyRevenueData() {
//   const result = [];
//   const currentDate = new Date();
//   for (let i = 0; i < 30; i++) {
//     const date = new Date(currentDate.getTime() - i * 24 * 60 * 60 * 1000);
//     result.push({
//       date: date.toISOString().split("T")[0], // 날짜 형식을 YYYY-MM-DD로 조정
//       value: Math.floor(Math.random() * 1000) + 100, // 100에서 1100 사이의 임의의 수익 값
//     });
//   }
//   return result.reverse(); // 최근 날짜가 먼저 오도록 배열 뒤집기
// } // 더미함수

export default function GetExposure({ advertId }: AdvertIdProps) {
  // const dates = getRecent30Days(); // 최근 30일 날짜 생성
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const [seriesData, setSeriesData] = useState<SeriesData[]>([
    { name: "광고시청수", data: [] }, // 초기 데이터 빈 배열로 설정
  ]);

  useEffect(() => {
    // const dummyData = generateDummyRevenueData();
    // const chartData = dummyData.map((item) => ({
    //   x: item.date,
    //   y: item.value,
    // }));
    // setSeriesData([{ name: "수익", data: chartData }]);
    if (advertId && accessToken) {
      getViewCount(advertId, accessToken).then((apiData) => {
        if (apiData && apiData.list) {
          const chartData = apiData.list.map(
            (item: { date: string; value: number }) => ({
              x: item.date,
              y: item.value,
            })
          );
          setSeriesData([{ name: "광고시청수", data: chartData }]);
        }
      });
    }
  }, [advertId, accessToken]);
  const [chartOptions, setChartOptions] = useState<ApexOptions>({
    chart: {
      type: "area",
      height: 350,
      zoom: {
        enabled: false,
      },
      stacked: true,
      dropShadow: {
        enabled: true,
        top: -2,
        left: 2,
        blur: 5,
        opacity: 0.06,
      },
    },
    colors: ["#6671AF"], // 그래프 색상 설정
    fill: {
      type: "solid", // 그라데이션 없이 단색으로 채우기
    },
    dataLabels: {
      enabled: false,
    },
    stroke: {
      curve: "straight",
    },
    title: {
      text: "광고 시청 수",
      align: "left",
      style: {
        fontFamily: "NotoSans KR",
        fontSize: "1.5rem",
        fontWeight: "800",
      },
      offsetY: 20, // 제목 위치 조정
    },
    subtitle: {
      text: "30일 동안의 광고 시청 수를 보여줍니다.",
      align: "left",
      style: {
        fontSize: "1.1rem",
        fontWeight: "500",
      },
      offsetY: 50, // 제목 위치 조정
    },
    // labels: dates,
    xaxis: {
      type: "datetime",
    },
    yaxis: {
      opposite: true,
    },
    legend: {
      horizontalAlign: "left",
    },
  });

  return (
    <div className={`${styles.mainChart}`}>
      <div id="chart">
        <ReactApexChart
          options={chartOptions}
          series={seriesData}
          type="area"
          height={280}
        />
      </div>
      <div id="html-dist"></div>
    </div>
  );
}
