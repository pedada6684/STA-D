import React, { useEffect, useState, useCallback } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { getAdList } from "../Select/userAdvertAPI";
import { getTotal } from "./DashboardAPI";
import { adList } from "../Select/SelectAdListBox";
import ReactApexChart from "react-apexcharts";
import styles from "./ChartData.module.css";
import { useQuery } from "react-query";

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

const PieChart: React.FC<PieChartProps> = ({ title, dataType }) => {
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const userId = useSelector((state: RootState) => state.user.userId);
  const [series, setSeries] = useState<number[]>([]);
  const [labels, setLabels] = useState<string[]>([]);
  const [loading, setLoading] = useState(true);
  const { data: pieAds } = useQuery(
    ["adsListPieData", userId, accessToken],
    () => getAdList(userId, accessToken),
    { enabled: !!userId }
  );

  const exampleAd: adList = { advertId: 1, title: "예시데이터" };

  const fetchData = useCallback(async () => {
    if (!pieAds || !pieAds.data) return;

    try {
      let combinedAds = pieAds.data;
      if (userId !== 1) {
        combinedAds = [exampleAd, ...pieAds.data];
      }

      const advertIds = combinedAds.map((ad: adList) => ad.advertId);
      const titles = combinedAds.map((ad: adList) => ad.title);

      const promises = advertIds.map((id: number) => getTotal(id, accessToken));
      const results = await Promise.all(promises);
      // 데이터 검사 시작
      console.log("Fetched Results:", results);
      results.forEach((res, index) => {
        console.log(`Result for ${titles[index]}:`, res);
        if (!res || typeof res[dataType] !== "number") {
          console.error(`Invalid data for ${titles[index]}:`, res);
        }
      });
      // 데이터 검사 종료
      const sortedResults = results
        .map((res, index) => ({
          value: res ? res[dataType] : 0,
          label: titles[index],
        }))
        .sort((a, b) => b.value - a.value)
        .slice(0, 5);

      setSeries(sortedResults.map((res) => res.value));
      setLabels(sortedResults.map((res) => res.label));
    } catch (error) {
      console.error("데이터 로드 실패", error);
    } finally {
      setLoading(false);
    }
  }, [pieAds, userId, accessToken, dataType]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const options: ApexCharts.ApexOptions = {
    chart: {
      type: "pie",
      height: 300,
      // animations: {
      //   enabled: true,
      //   easing: "easeinout",
      //   speed: 800,
      //   animateGradually: {
      //     enabled: true,
      //     delay: 150,
      //   },
      //   dynamicAnimation: {
      //     enabled: true,
      //     speed: 350,
      //   },
      // },
    },
    colors: ["#3552F2", "#6D81F2", "#A0ACF2", "#C9D3F2", "#C9CCD8"],
    dataLabels: {
      enabled: true,
    },
    stroke: {
      width: 0,
    },
    labels: labels,
    plotOptions: {
      pie: {
        donut: {
          size: "50%",
        },
      },
    },
    // responsive: [
    //   {
    //     breakpoint: 480,
    //     options: {
    //       chart: { width: 200 },
    //       legend: {
    //         show: true,
    //         position: "left",
    //         horizontalAlign: "center",
    //         verticalAlign: "middle",
    //         offsetX: 0,
    //         offsetY: 0,
    //       },
    //     },
    //   },
    // ],
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className={`${styles.container} ${styles.pie}`}>
      <div className={`${styles.title}`}>{title}</div>
      <div className={`${styles.pieContainer}`}>
        <ReactApexChart
          options={options}
          series={series}
          type="pie"
          width={350}
          height={350}
        />
      </div>
    </div>
  );
};

export default PieChart;
