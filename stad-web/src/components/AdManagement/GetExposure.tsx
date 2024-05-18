import { ApexOptions } from "apexcharts";
import { useEffect, useState } from "react";
import ReactApexChart from "react-apexcharts";
import styles from "./ChartData.module.css";
import { AdvertIdProps } from "../../pages/AdManagement/All/AllExposure";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { getViewCount } from "./DashboardAPI";
import Loading from "../Loading";
interface SeriesData {
  name: string;
  data: { x: string; y: number }[];
}

export default function GetExposure({ advertId }: AdvertIdProps) {
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const [seriesData, setSeriesData] = useState<SeriesData[]>([
    { name: "광고시청수", data: [] }, // 초기 데이터 빈 배열로 설정
  ]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (advertId && accessToken) {
      setLoading(true);
      getViewCount(advertId, accessToken)
        .then((apiData) => {
          const chartData =
            apiData?.list?.map((item: { date: string; value: number }) => ({
              x: item.date,
              y: item.value,
            })) ?? [];
          setSeriesData([{ name: "광고시청수", data: chartData }]);
          console.log(seriesData);
          setLoading(false);
        })
        .catch((error) => {
          setSeriesData([{ name: "광고시청수", data: [] }]);
          setLoading(false);
        });
    }
  }, [advertId, accessToken]);
  const chartOptions: ApexOptions = {
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
      text: "광고 시청 수",
      align: "left",
      style: {
        fontFamily: "Noto Sans KR",
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
    grid: {
      show: false, // 배경 그리드 라인 제거
    },
  };

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
    ); // 데이터가 없는 경우
  }
  return (
    <div className={`${styles.mainChart}`}>
      <div id="chart">
        <ReactApexChart
          options={chartOptions}
          series={seriesData}
          type="area"
          height={260}
        />
      </div>
      <div id="html-dist"></div>
    </div>
  );
}
