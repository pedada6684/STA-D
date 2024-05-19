import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useEffect, useState } from "react";
import { getAdList } from "../Select/userAdvertAPI";
import { getTotal } from "./DashboardAPI";
import { adList } from "../Select/SelectAdListBox";
import ReactApexChart from "react-apexcharts";
import styles from "./ChartData.module.css";
import Loading from "../Loading";
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

export default function PieChart({ title, dataType }: PieChartProps) {
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const userId = useSelector((state: RootState) => state.user.userId);
  const [series, setSeries] = useState<number[]>([]);
  const [labels, setLabels] = useState<string[]>([]);
  const [loading, setLoading] = useState(true);
  const { data: ads, isLoading } = useQuery(
    ["adsListData", userId, accessToken],
    () => getAdList(userId, accessToken),
    {
      enabled: !!userId,
    }
  );
  const exampleAd: adList = {
    advertId: 1,
    title: "예시데이터",
  };
  useEffect(() => {
    async function fetchData() {
      try {
        if (ads && ads.data) {
          setLoading(true);
          let combinedAds = ads.data;
          if (userId !== 1) {
            combinedAds = [exampleAd, ...ads.data];
          }
          // const adListData = await getAdList(userId, accessToken); // 유저 광고 리스트 조회
          // console.log("광고 리스트 조회 성공 파이", adListData);
          const advertIds = combinedAds.map((ad: adList) => ad.advertId); // map 사용해서 advertId 담아주기
          console.log(advertIds);
          const titles = combinedAds.map((ad: adList) => ad.title);
          // const promise = advertIds.map((id: number) => {
          //   console.log(id);
          //   getTotal(id, accessToken);
          // });
          const promises = advertIds.map((id: number) =>
            getTotal(id, accessToken)
          );
          const results = await Promise.all(promises);
          console.log("------------------");
          console.log("각 광고의 총합 데이터 조회 성공", results);

          //데이터 pie chart 형식에 맞게 변환

          const sortedResults = results
            .map((res, index) => ({
              value: res[dataType],
              label: titles[index],
            }))
            .sort((a, b) => b.value - a.value) // 값에 따라 내림차순으로 정렬
            .slice(0, 5); // 상위 5개 항목만 선택
          console.log("sortedResults?", sortedResults);

          // 상태 업데이트 전 로그 출력
          console.log("Updating series and labels");
          console.log(
            "Series to set:",
            sortedResults.map((res) => res.value)
          );
          console.log(
            "Labels to set:",
            sortedResults.map((res) => res.label)
          );

          setSeries(sortedResults.map((res) => res.value));
          setLabels(sortedResults.map((res) => res.label));
        }
      } catch (error) {
        console.error("데이터 로드 실패", error);
      }
    }
    fetchData();
  }, [ads, accessToken, dataType]);

  useEffect(() => {
    console.log("Series updated:", series);
  }, [series]);

  const options: ApexCharts.ApexOptions = {
    chart: {
      type: "donut",
      height: 300,
      animations: {
        enabled: true,
        easing: "easeinout",
        speed: 800,
        animateGradually: {
          enabled: true,
          delay: 150,
        },
        dynamicAnimation: {
          enabled: true,
          speed: 350,
        },
      },
    },
    colors: ["#3552F2", "#6D81F2", "#A0ACF2", "#C9D3F2", "#C9CCD8"],
    dataLabels: {
      enabled: true,
    },
    stroke: {
      width: 0, // 테두리 제거
    },

    labels: labels,
    plotOptions: {
      pie: {
        donut: {
          size: "50%", // 내부 반경 크기 조절
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
          options={options}
          series={series}
          type="donut"
          width={400}
          height={400}
        />
      </div>
    </div>
  );
}
