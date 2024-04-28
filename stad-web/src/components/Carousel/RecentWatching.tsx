import { SmallNextArrow, SmallPrevArrow } from "../Arrow/Arrow";
import { smallThumbnail } from "../../pages/Category/SeriesDummy";
import "./RecentWatching.css";
import Slider from "react-slick";
export default function RecentWatching() {
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
        <Slider {...setting}>
          {smallThumbnail.map((data, index) => (
            <div
              className="s-vid-container"
              key={index}
              style={{ position: "relative", transition: "all 0.3s" }}
            >
              <img src={data.url} alt="비디오 썸네일" />
              <div className="vidTitle">{data.title}</div>
            </div>
          ))}
        </Slider>
      </div>
    </div>
  );
}
