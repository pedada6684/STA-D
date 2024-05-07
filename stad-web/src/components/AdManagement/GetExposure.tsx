import { ApexOptions } from "apexcharts";
import { useEffect, useState } from "react";
import ReactApexChart from "react-apexcharts";
import styles from "./ChartData.module.css";
import { AdvertIdProps } from "../../pages/AdManagement/All/AllExposure";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { getViewCount } from "./DashboardAPI";

export default function GetExposure({ advertId }: AdvertIdProps) {
  // const dates = getRecent30Days(); // 최근 30일 날짜 생성
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const [seriesData, setSeriesData] = useState([
    { name: "광고시청수", data: [] }, // 초기 데이터 빈 배열로 설정
  ]);

  useEffect(() => {
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
    colors: ["#00127A"], // 그래프 색상 설정
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
