import { SmallNextArrow, SmallPrevArrow } from "../Arrow/Arrow";
import { smallThumbnail } from "../../pages/Category/SeriesDummy";
import "./CategoryCarousel.css";
import Slider from "react-slick";
interface CategoryCarouselProps {
  title?: string;
  items?: { url: string; title: string }[];
  marginTop?: string;
  marginBottom?: string;
}

export default function CategoryCarousel({
  title,
  items = [],
  marginTop,
  marginBottom,
}: CategoryCarouselProps) {
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
    <div
      className="main-container"
      style={{ marginTop: marginTop, marginBottom: marginBottom }}
    >
      <div className="v-title">{title}</div>
      <div className="thumbnail-container">
        <Slider {...setting}>
          {items.map((data, index) => (
            <div
              className="xs-vid-container"
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
