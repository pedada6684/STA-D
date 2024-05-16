import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useEffect, useState } from "react";
import { getAdList } from "../Select/userAdvertAPI";
import { getTotal } from "./DashboardAPI";
import { adList } from "../Select/SelectAdListBox";
import ReactApexChart from "react-apexcharts";
import styles from "./ChartData.module.css";
import Loading from "../Loading";
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
  const [series, setSeries] = useState<number[]>([]);
  const [labels, setLabels] = useState<string[]>([]);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    async function fetchData() {
      try {
        setLoading(true);
        const adListData = await getAdList(userId, accessToken); // 유저 광고 리스트 조회
        console.log("광고 리스트 조회 성공 파이", adListData);
        const advertIds = adListData?.data?.map((ad: adList) => ad.advertId); // map 사용해서 advertId 담아주기
        const promise = advertIds.map((id: number) =>
          getTotal(id, accessToken)
        );
        const results = await Promise.all(promise); // 데이터 병렬을 위해 동기
        console.log("각 광고의 총합 데이터 조회 성공", results);

        //데이터 pie chart 형식에 맞게 변환

        const sortedResults = results
          .map((res, index) => ({
            value: res[dataType],
            label: adListData.data[index].title,
          }))
          .sort((a, b) => b.value - a.value) // 값에 따라 내림차순으로 정렬
          .slice(0, 5); // 상위 5개 항목만 선택
        console.log("sortedResults?", sortedResults);
        if (sortedResults.length > 0) {
          setSeries(sortedResults.map((res) => res.value));
          setLabels(sortedResults.map((res) => res.label));
          console.log("시리즈값:", series, "+", "라벨값: ", labels);
          setLoading(false);
        } else {
          setSeries([0]);
          setLabels(["No Data"]);
        }
        console.log("시리즈값: ", series);
        console.log("라벨 값: ", labels);
      } catch (error) {
        console.error("데이터 로드 실패", error);
        setSeries([0]);
        setLabels(["No Data"]);
      } finally {
        setLoading(false);
      }
    }
    fetchData();
  }, [accessToken, dataType]);

  useEffect(() => {
    console.log("시리즈값:", series);
    console.log("라벨값:", labels);
  }, [series, labels]);

  if (loading) {
    return (
      <>
        <Loading />
      </>
    );
  }
  const chartOptions: ApexCharts.ApexOptions = {
    chart: { type: "donut", height: 300 },
    colors: ["#3552F2", "#6D81F2", "#A0ACF2", "#C9D3F2", "#C9CCD8"],
    dataLabels: {
      enabled: false,
    },
    stroke: {
      width: 0, // 테두리 제거
    },
    labels: labels,
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
