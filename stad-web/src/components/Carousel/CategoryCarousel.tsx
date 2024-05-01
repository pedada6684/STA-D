import { SmallNextArrow, SmallPrevArrow } from "../Arrow/Arrow";
import "./CategoryCarousel.css";
import Slider from "react-slick";
import { useNavigate } from "react-router-dom";

// getConceptResponseList 인터페이스 정의
export interface getConceptResponseList {
  title: string;
  thumbnailUrl: string;
  conceptId: number;
}

// CategoryCarouselProps 인터페이스 정의
export interface CategoryCarouselProps {
  title: string;
  items: getConceptResponseList[];
  marginTop?: string;
  marginBottom?: string;
}

export default function CategoryCarousel({
  title,
  items = [],
  marginTop,
  marginBottom,
}: CategoryCarouselProps) {
  const navigate = useNavigate();

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
          {items.map((data: getConceptResponseList, index: number) => (
            <div
              className="xs-vid-container"
              key={index}
              style={{ position: "relative", transition: "all 0.3s" }}
              onClick={() => navigate(`/tv/${data.conceptId}`)}
            >
              <img src={data.thumbnailUrl} alt="비디오 썸네일" />
              <div className="vidTitle">{data.title}</div>
            </div>
          ))}
        </Slider>
      </div>
    </div>
  );
}
