import { useSelector } from "react-redux";
import { AdvertIdProps } from "../../pages/AdManagement/All/AllExposure";
import { RootState } from "../../store";
import { useEffect, useState } from "react";
import { getOrderCount } from "./DashboardAPI";
import { ApexOptions } from "apexcharts";
import styles from "./FrameComponent.module.css";
import ReactApexChart from "react-apexcharts";

export default function GetOrder({ advertId }: AdvertIdProps) {
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const [seriesData, setSeriesData] = useState([
    { name: "주문건수", data: [] }, // 초기 데이터 빈 배열로 설정
  ]);

  useEffect(() => {
    if (advertId && accessToken) {
      getOrderCount(advertId, accessToken).then((apiData) => {
        if (apiData && apiData.list) {
          const chartData = apiData.list.map(
            (item: { date: string; value: number }) => ({
              x: item.date,
              y: item.value,
            })
          );
          setSeriesData([{ name: "주문건수", data: chartData }]);
        }
      });
    }
  }, [advertId, accessToken]);
  const [chartOptions, setChartOptions] = useState<ApexOptions>({
    chart: {
      id: "area-datetime",
      type: "area",
      height: 350,
      zoom: {
        autoScaleYaxis: true,
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
    dataLabels: {
      enabled: false,
    },
    stroke: {
      curve: "straight",
    },
    title: {
      text: "주문건수",
      align: "left",
      style: {
        fontFamily: "NotoSans KR",
        fontSize: "1.5rem",
        fontWeight: "800",
      },
      offsetY: 20, // 제목 위치 조정
    },
    subtitle: {
      text: "30일 동안의 주문건수를 보여줍니다.",
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
          height={300}
        />
      </div>
      <div id="html-dist"></div>
    </div>
  );
}
