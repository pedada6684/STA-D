import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useNavigate } from "react-router-dom";
import { useQuery } from "react-query";
import { CarouselVideoProps } from "./MainCarousel";
import { GetSaveWatching } from "./CarouselApI";
import Loading from "../Loading";
import { SmallNextArrow, SmallPrevArrow } from "../Arrow/Arrow";
import Slider from "react-slick";

export default function SaveCarousel() {
  const userId = useSelector(
    (state: RootState) => state.tvUser.selectedProfile?.userId
  );
  const navigate = useNavigate();
  const { data: saveData, isLoading } = useQuery<CarouselVideoProps[]>(
    "saveList",
    () => GetSaveWatching(userId!),
    { enabled: !!userId }
  );
  if (isLoading)
    return (
      <div>
        <Loading />
      </div>
    );
  let setting = {
    dots: true,
    infinite: false,
    slidesToShow: 4,
    slidesToScroll: 1,
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
      <div className="v-title">내가 찜한 컨텐츠</div>
      <div className="thumbnail-container">
        {saveData && saveData.length > 0 ? (
          <Slider {...setting}>
            {saveData?.map((data, index) => (
              <div
                className="s-vid-container"
                key={index}
                style={{ position: "relative", transition: "all 0.3s" }}
                onClick={() => navigate(`/tv/${data.conceptId}`)}
              >
                <img src={data.thumbnailUrl} alt="비디오 썸네일" />
                <div className="vidTitle">{data.title}</div>
              </div>
            ))}
          </Slider>
        ) : (
          <div className="no-content">찜한 영상이 없습니다.</div>
        )}
      </div>
    </div>
  );
}
