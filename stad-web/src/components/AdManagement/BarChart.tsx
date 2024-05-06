import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useEffect, useState } from "react";
import ReactApexChart from "react-apexcharts";
import { PieChartProps } from "./PieChart";
import styles from "./ChartData.module.css";
interface SeriesType {
  name?: string;
  data: number[];
}
export default function BarChart({ title, dataType }: PieChartProps) {
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const userId = useSelector((state: RootState) => state.user.userId);

  const [series, setSeries] = useState<SeriesType[]>([
    { name: `${title}`, data: [50, 30, 20, 15] },
  ]);
  const [labels, setLabels] = useState<string[]>([
    "Advert A",
    "Advert B",
    "Advert C",
    "Advert D",
  ]);
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
    //     setSeries(results.map((res) => ({ name: res[dataType].name, data: res[dataType].data })));
    //     setLabels(adListData.data.map((ad: adList) => ad.title));
    //   } catch (error) {
    //     console.error("데이터 로드 실패", error);
    //   }
    // }
    // fetchData();
  }, [accessToken, dataType]);

  const options: ApexCharts.ApexOptions = {
    chart: {
      type: "bar",
      height: 200,
    },
    colors: ["#FEB019", "#FFDA00", "#F9ECAF", "#FFF9DF"],
    plotOptions: {
      bar: {
        horizontal: true, // 가로 바 차트
      },
    },
    dataLabels: {
      enabled: true,
    },
    xaxis: {
      categories: labels,
    },
    title: {
      text: title,
      align: "center",
    },
  };

  return (
    <div className={`${styles.container} ${styles.barContainer}`}>
      <div className={`${styles.title}`}>{title}</div>
      <div>
        <ReactApexChart
          options={options}
          series={series}
          type="bar"
          height={200}
        />
      </div>
    </div>
  );
}
