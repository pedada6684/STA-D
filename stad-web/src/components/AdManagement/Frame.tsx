import FrameComponent from "./FrameComponent";
import eye from "../../assets/mdi_eye.png";
import click from "../../assets/fluent_cursor-click-20-regular.png";
import order from "../../assets/icon-park-outline_order.png";
import money from "../../assets/game-icons_money-stack.png";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { AdvertIdProps } from "../../pages/AdManagement/All/AllExposure";
import { useQuery } from "react-query";
import {
  getClickCount,
  getOrderCount,
  getRevenue,
  getViewCount,
} from "./DashboardAPI";
import Loading from "../Loading";
import styles from "./FrameComponent.module.css";
export default function Frame({ advertId }: AdvertIdProps) {
  const token = useSelector((state: RootState) => state.token.accessToken);
  // 광고 시청 수 가져오기
  const { data: viewCountData, isLoading: viewCountLoading } = useQuery(
    ["viewCount", advertId, token],
    () => getViewCount(advertId, token)
  );

  // 광고 클릭 수 가져오기
  const { data: clickCountData, isLoading: clickCountLoading } = useQuery(
    ["clickCount", advertId, token],
    () => getClickCount(advertId, token)
  );

  // 광고 주문 수 가져오기
  const { data: orderCountData, isLoading: orderCountLoading } = useQuery(
    ["orderCount", advertId, token],
    () => getOrderCount(advertId, token)
  );

  // 광고 수익 가져오기
  const { data: revenueData, isLoading: revenueLoading } = useQuery(
    ["revenue", advertId, token],
    () => getRevenue(advertId, token)
  );

  if (
    viewCountLoading ||
    clickCountLoading ||
    orderCountLoading ||
    revenueLoading
  ) {
    return <Loading />;
  }
  return (
    <div className={`${styles.Wrapper}`}>
      <FrameComponent
        icon={eye}
        title="오늘의 광고 시청 수"
        value={viewCountData?.list[viewCountData?.list.length - 1]?.value}
        unit="건"
        className="yellow"
        iconContainer="w"
      />
      <FrameComponent
        icon={click}
        title="오늘의 광고 클릭 수"
        value={clickCountData?.list[clickCountData?.list.length - 1]?.value}
        unit="건"
        className="white"
        iconContainer="y"
      />
      <FrameComponent
        icon={order}
        title="오늘의 광고 주문 수"
        unit="건"
        value={orderCountData?.list[orderCountData?.list.length - 1]?.value}
        className="yellow"
        iconContainer="w"
      />
      <FrameComponent
        icon={money}
        title="오늘의 광고 수익"
        unit="₩"
        value={revenueData?.list[revenueData?.list.length - 1]?.value}
        className="white"
        iconContainer="y"
      />
    </div>
  );
}
