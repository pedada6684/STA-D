import { useSelector } from "react-redux";
import { AdvertIdProps } from "../../pages/AdManagement/All/AllExposure";
import { RootState } from "../../store";
import { useEffect, useState } from "react";
import { getOrderCount } from "./DashboardAPI";
import { ApexOptions } from "apexcharts";
import styles from "./ChartData.module.css";
import ReactApexChart from "react-apexcharts";
import Loading from "../Loading";

export default function GetOrder({ advertId }: AdvertIdProps) {
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const [seriesData, setSeriesData] = useState([
    { name: "주문건수", data: [] }, // 초기 데이터 빈 배열로 설정
  ]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (advertId && accessToken) {
      setLoading(true);
      getOrderCount(advertId, accessToken)
        .then((apiData) => {
          const chartData =
            apiData.list.map((item: { date: string; value: number }) => ({
              x: item.date,
              y: item.value,
            })) ?? [];
          setSeriesData([{ name: "주문건수", data: chartData }]);
          setLoading(false);
        })
        .catch((error) => {
          setSeriesData([{ name: "수익", data: [] }]);
          setLoading(false);
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
    colors: ["#ECEDFF"], // 그래프 채우기 색상 설정
    fill: {
      type: "solid", // 그라데이션 없이 단색으로 채우기
    },
    dataLabels: {
      enabled: false,
    },
    stroke: {
      curve: "straight",
      colors: ["#ADB2FF"], // 그래프 경계선 색상 설정
    },
    title: {
      text: "주문건수",
      align: "left",
      style: {
        fontFamily: "Noto Sans KR",
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
    grid: {
      show: false, // 배경 그리드 라인 제거
    },
  });

  if (loading) {
    return (
      <>
        <Loading />
      </>
    );
  }
  if (!seriesData || seriesData[0].data.length === 0) {
    return (
      <div>
        <Loading />
      </div>
    );
  }
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
