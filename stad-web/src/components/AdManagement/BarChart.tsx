import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useEffect, useState } from "react";
import ReactApexChart from "react-apexcharts";
import { PieChartProps } from "./PieChart";
import styles from "./ChartData.module.css";
import { getAdList } from "../Select/userAdvertAPI";
import { adList } from "../Select/SelectAdListBox";
import { getTotal } from "./DashboardAPI";
import { useQuery } from "react-query";
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
    { name: `${title}`, data: [] },
  ]);
  const [labels, setLabels] = useState<string[]>([]);

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
          let combinedAds = ads.data;
          if (userId !== 1) {
            combinedAds = [exampleAd, ...ads.data];
          }
          // const combinedAds = [exampleAd, ...ads.data];
          // advertId와 title 추출

          const advertIds = combinedAds.map((ad: adList) => ad.advertId);
          const titles = combinedAds.map((ad: adList) => ad.title);
          // getTotal API 호출
          const promise = advertIds.map((id: number) =>
            getTotal(id, accessToken)
          );
          const results = await Promise.all(promise);
          // 결과 정렬 및 상위 5개 항목 선택
          const sortedResults = results
            .map((res, index) => ({
              value: res[dataType],
              label: titles[index],
            }))
            .sort((a, b) => b.value - a.value) // 값에 따라 내림차순으로 정렬
            .slice(0, 5); // 상위 5개 항목만 선택

          setSeries([
            { name: title, data: sortedResults.map((res) => res.value) },
          ]);
          setLabels(sortedResults.map((res) => res.label));
        }
      } catch (error) {
        console.error("데이터 로드 실패", error);
      }
    }
    fetchData();
  }, [ads, accessToken, dataType]);

  const options: ApexCharts.ApexOptions = {
    chart: {
      type: "bar",
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
    plotOptions: {
      bar: {
        horizontal: true, // 가로 바 차트
        borderRadius: 3, // 바 모양에 border-radius 추가
        distributed: true, // 바마다 다른 색상
        barHeight: "20%", // 바 사이에 간격 추가
      },
    },
    dataLabels: {
      enabled: true,
    },
    xaxis: {
      categories: labels,
    },
    yaxis: {
      labels: {
        style: {
          fontSize: "12px", // y축 레이블 폰트 크기 조정
        },
      },
    },
    title: {
      // text: "30일 동안 광고 시청 건수",
      align: "left",
      style: {
        fontFamily: "Noto Sans KR",
        fontSize: "1.5rem",
        fontWeight: "700",
      },
      offsetY: 100, // 제목 위치 조정
    },
    grid: {
      show: false, // 배경에 줄 안 보이게 설정
    },
  };

  return (
    <div className={`${styles.container} ${styles.barContainer}`}>
      <div className={`${styles.title}`}>{title}</div>
      <div className={`${styles.barWrapper}`}>
        <ReactApexChart
          options={options}
          series={series}
          type="bar"
          height={270}
        />
      </div>
    </div>
  );
}
