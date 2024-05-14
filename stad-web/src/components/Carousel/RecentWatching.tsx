import { SmallNextArrow, SmallPrevArrow } from "../Arrow/Arrow";
import { smallThumbnail } from "../../pages/Category/SeriesDummy";
import "./RecentWatching.css";
import Slider from "react-slick";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useQuery } from "react-query";
import { CarouselVideoProps } from "./MainCarousel";
import { GetRecentWatching } from "./CarouselApI";
import Content from "../Container/Content";
export default function RecentWatching() {
  const userId = useSelector((state: RootState) => state.tvUser.userId);
  const navigate = useNavigate();
  const {
    data: WatchingData,
    isLoading,
    error,
  } = useQuery<CarouselVideoProps[]>("watching", () =>
    GetRecentWatching(userId)
  );
  if (isLoading)
    return (
      <div>
        <Content>Loading...</Content>
      </div>
    );

  let setting = {
    dots: true,
    infinite: true,
    slidesToShow: 4,
    slidesToScroll: 4,
    speed: 500,
    appendDots: (dots: any) => (
      <div
        style={{
          position: "absolute",
          display: "flex",
          alignItems: "center",
        }}
      >
        <ul> {dots} </ul>
      </div>
    ),
    dotsClass: "dots_custom",
    prevArrow: <SmallPrevArrow />,
    nextArrow: <SmallNextArrow />,
  };
  return (
    <div className="v-container">
      <div className="v-title">최근 시청중인 컨텐츠</div>
      <div className="thumbnail-container">
        {WatchingData && WatchingData.length > 0 ? (
          <Slider {...setting}>
            {WatchingData?.map((data, index) => (
              <div
                className="s-vid-container"
                key={index}
                style={{ position: "relative", transition: "all 0.3s" }}
                onClick={() => navigate(`/tv/${data.detailId}`)}
              >
                <img src={data.thumbnailUrl} alt="비디오 썸네일" />
                <div className="vidTitle">{data.title}</div>
              </div>
            ))}
          </Slider>
        ) : (
          <div className="no-content">현재 시청 중인 영상이 없습니다.</div>
        )}
      </div>
    </div>
  );
}
